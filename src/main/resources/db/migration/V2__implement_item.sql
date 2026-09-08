-- 1. Crear la tabla padre 'items'
CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- 2. Migrar 'product' manteniendo sus IDs exactos
INSERT INTO items (id, name, description, active)
SELECT id, name, description, COALESCE(active, TRUE) 
FROM product;

-- Ajustar el autoincremento de 'items' para los futuros registros
SET @max_prod_id = (SELECT IFNULL(MAX(id), 0) FROM product);
SET @sql_auto_inc = CONCAT('ALTER TABLE items AUTO_INCREMENT = ', @max_prod_id + 1);
PREPARE stmt FROM @sql_auto_inc;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3. Migrar 'suplies' a 'items' generando nuevos IDs automáticamente
INSERT INTO items (name, description, active)
SELECT name, description, COALESCE(active, TRUE) 
FROM suplies;

-- 4. Actualizar los IDs de la tabla 'suplies' haciendo match con los nuevos registros insertados en 'items'
-- (Esto asume que los nombres/descripciones son únicos o que suplies no tiene dependencias externas críticas de IDs antiguos)
UPDATE suplies s
JOIN items i ON s.name = i.name 
            AND IFNULL(s.description, '') = IFNULL(i.description, '') 
            AND i.id > @max_prod_id
SET s.id = i.id;

-- 5. Limpiar columnas heredadas en 'product' y agregar la llave foránea
ALTER TABLE product 
    DROP COLUMN name,
    DROP COLUMN description,
    DROP COLUMN active;

ALTER TABLE product 
    ADD CONSTRAINT fk_product_item FOREIGN KEY (id) REFERENCES items(id) ON DELETE CASCADE;

-- 6. Limpiar columnas heredadas en 'suplies' y agregar la llave foránea
ALTER TABLE suplies 
    DROP COLUMN name,
    DROP COLUMN description,
    DROP COLUMN active;

ALTER TABLE suplies 
    ADD CONSTRAINT fk_suplies_item FOREIGN KEY (id) REFERENCES items(id) ON DELETE CASCADE;