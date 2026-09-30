ALTER TABLE categories_expense
ADD active bit(1) DEFAULT NULL;

RENAME categories_expense TO expense_categories;