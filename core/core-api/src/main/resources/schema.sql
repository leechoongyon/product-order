create TABLE IF NOT EXISTS product_order (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  order_token VARCHAR(255) NOT NULL,
  product_id VARCHAR(255) NOT NULL,
  user_id VARCHAR(255) NOT NULL,
  quantity INTEGER NOT NULL,
  ordered_at TIMESTAMP NOT NULL
);
