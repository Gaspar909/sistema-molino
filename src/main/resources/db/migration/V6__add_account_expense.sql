/*Agrega la columna account_id a la tabala expenses */
ALTER TABLE expenses
ADD account_id BIGINT(20) DEFAULT NULL;

/* Agrega la relación entre expenses y account */ 
ALTER TABLE expenses
ADD CONSTRAINT fk_expenses_account FOREIGN KEY (account_id) REFERENCES accounts(id);
