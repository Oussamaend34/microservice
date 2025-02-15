CREATE TABLE orders
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    order_number VARCHAR(255) NULL,
    sku_code     VARCHAR(255) NULL,
    price        DECIMAL(19, 2)      NULL,
    quantity     INT          NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);