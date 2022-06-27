SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE item;
TRUNCATE TABLE order_table;
TRUNCATE TABLE order_item;
SET REFERENTIAL_INTEGRITY TRUE;
ALTER TABLE item ALTER COLUMN id RESTART WITH 1;
ALTER TABLE order_table ALTER COLUMN id RESTART WITH 1;
ALTER TABLE order_item ALTER COLUMN id RESTART WITH 1;

INSERT INTO item(id, sku, item_name, price) VALUES(1, 'MENS-156', 'Small Black T-Shirt', 50);
INSERT INTO item(id, sku, item_name, price) VALUES(2, 'NIKE-7', 'Nike Air Jordans - Size 7', 110.75);
INSERT INTO item(id, sku, item_name, price) VALUES(3, 'SOC-1', 'Soccer Ball', 25.99);
INSERT INTO item(id, sku, item_name, price) VALUES(4, 'MU-5091', 'Lip Gloss', 15.25);
INSERT INTO item(id, sku, item_name, price) VALUES(5, 'NIKE-56', 'Nike Medium Red Leggings', 75.5);
INSERT INTO item(id, sku, item_name, price) VALUES(6, 'MU-4129', 'Eye Shadow', 28.5);
INSERT INTO item(id, sku, item_name, price) VALUES(7, 'PAR-14', 'Chanel - CHANCE EAU FRAÎCHE Eau de Toilette', 149.99);
INSERT INTO item(id, sku, item_name, price) VALUES(8, 'NIKE-143', 'Nike Mercurial Superfly 8 Elite FG Firm Ground Soccer Cleat', 24.99);

INSERT INTO order_table(id, order_id, email_address) VALUES(1, 'RK-478', 'john@example.com');
INSERT INTO order_table(id, order_id, email_address) VALUES(2, 'RK-642', 'will@example.com');
INSERT INTO order_table(id, order_id, email_address) VALUES(3, 'RK-238', 'carly@example.com');
INSERT INTO order_table(id, order_id, email_address) VALUES(4, 'RK-912', 'karen@example.com');
INSERT INTO order_table(id, order_id, email_address) VALUES(5, 'RK-239', 'steve@example.com');
INSERT INTO order_table(id, order_id, email_address) VALUES(6, 'RK-149', 'dalton@example.com');

INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(1, 1, 1, 2);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(2, 1, 2, 1);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(3, 2, 3, 2);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(4, 3, 4, 3);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(5, 3, 5, 1);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(6, 3, 6, 2);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(7, 4, 7, 1);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(8, 5, 1, 1);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(9, 6, 3, 1);
INSERT INTO order_item(id, order_id, item_id, quantity) VALUES(10, 6, 8, 1);
