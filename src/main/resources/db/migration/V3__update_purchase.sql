-- 1. Modificaciones en la tabla purchases
ALTER TABLE purchases 
    ADD COLUMN account_id BIGINT,
    ADD COLUMN user_id BIGINT NOT NULL;

-- Agrega las llaves foráneas: 'accounts' y 'users'
ALTER TABLE purchases
    ADD CONSTRAINT fk_purchases_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    ADD CONSTRAINT fk_purchases_user FOREIGN KEY (user_id) REFERENCES users(id);


-- 2. Modificaciones en la tabla purchase_datails
-- Asegurar que purchase_id no sea nulo
ALTER TABLE purchase_datails 
    CHANGE purchase_id purchase_id  BIGINT NOT NULL;

-- 3. Modificar reference_id para que sea la FK hacia la tabla 'items'
ALTER TABLE purchase_datails
    ADD CONSTRAINT fk_purchase_details_item FOREIGN KEY (reference_id) REFERENCES items(id);