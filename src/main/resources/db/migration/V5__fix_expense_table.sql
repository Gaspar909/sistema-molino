ALTER TABLE categories_expense
ADD IF NOT EXISTS active bit(1) DEFAULT NULL;

RENAME TABLE categories_expense TO expense_categories;