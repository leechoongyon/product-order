-- product_order ddl
create TABLE IF NOT EXISTS product_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_token VARCHAR(255) NOT NULL,
  product_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  quantity INTEGER NOT NULL,
  ordered_at TIMESTAMP NOT NULL
);

-- product ddl
create TABLE IF NOT EXISTS product (
  id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  stock INT NOT NULL
);

-- product dml
insert into product (id, name, stock)
values
  ('product1001', 'Product A', 1000),
  ('product1002', 'Product B', 2000),
  ('product1003', 'Product C', 3000),
  ('product1004', 'Product D', 4000),
  ('product1005', 'Product E', 5000);