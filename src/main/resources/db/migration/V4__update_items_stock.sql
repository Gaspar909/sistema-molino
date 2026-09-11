-- 1. Agregar la columna unificada en la tabla 'items' (la superclase)
ALTER TABLE items 
    ADD COLUMN stock DECIMAL(32,8);

-- 2. Migrar los datos desde 'product' (si aplica)
UPDATE items i
JOIN product p ON i.id = p.id
SET i.stock = p.stock;

-- 3. Migrar los datos desde 'suplies' (si aplica)
UPDATE items i
JOIN suplies s ON i.id = s.id
SET i.stock = s.stock;

-- 4. Eliminar las columnas redundantes de las tablas hijas
ALTER TABLE product 
    DROP COLUMN stock;

ALTER TABLE suplies 
    DROP COLUMN stock;