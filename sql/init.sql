-- =============================================================
-- 电商系统商品管理模块：建库、建表与测试数据脚本
-- 数据库：MySQL 8.x    字符集：utf8mb4
-- =============================================================

-- 1. 创建数据库（如已存在可跳过）
CREATE DATABASE IF NOT EXISTS ecommerce_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE ecommerce_db;

-- 2. 创建商品表 t_product
DROP TABLE IF EXISTS t_product;
CREATE TABLE t_product (
    id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
    name         VARCHAR(100) NOT NULL COMMENT '商品名称',
    price        DECIMAL(10,2) NOT NULL COMMENT '价格',
    stock        INT          NOT NULL DEFAULT 0 COMMENT '库存',
    category_id  BIGINT       DEFAULT NULL COMMENT '分类 ID',
    image_url    VARCHAR(255) DEFAULT NULL COMMENT '图片地址',
    description  VARCHAR(500) DEFAULT NULL COMMENT '商品描述',
    status       TINYINT      NOT NULL DEFAULT 1 COMMENT '状态：1 上架 / 0 下架',
    create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT ='商品表';

-- 3. 插入测试数据
INSERT INTO t_product (name, price, stock, category_id, image_url, description, status, create_time) VALUES
('华为 Mate 60 手机', 4999.00, 50, 1, 'https://example.com/p1.jpg', '麒麟芯片，卫星通信', 1, '2026-09-01 10:00:00'),
('小米 14 手机',      3999.00, 80, 1, 'https://example.com/p2.jpg', '骁龙旗舰，徕卡影像', 1, '2026-09-02 11:00:00'),
('iPhone 15',        5999.00, 30, 1, 'https://example.com/p3.jpg', 'A16 芯片，灵动岛', 1, '2026-09-03 09:30:00'),
('无线蓝牙耳机',      299.00, 200, 2, 'https://example.com/p4.jpg', '主动降噪，长续航', 1, '2026-09-04 14:20:00'),
('机械键盘 K87',      349.00, 120, 2, 'https://example.com/p5.jpg', '青轴热插拔，RGB 背光', 1, '2026-09-05 16:40:00'),
('无线鼠标',          129.00, 150, 2, 'https://example.com/p6.jpg', '轻量办公鼠标', 0, '2026-09-06 08:10:00'),
('轻薄笔记本电脑',    6299.00, 20, 3, 'https://example.com/p7.jpg', '14 英寸，16G 内存', 1, '2026-09-07 19:00:00'),
('平板电脑 Pro',      3299.00, 40, 3, 'https://example.com/p8.jpg', '高刷屏，触控笔支持', 1, '2026-09-08 20:30:00');

-- 4. 常用验证查询（供实验时手工核对）
-- SELECT * FROM t_product WHERE status = 1 ORDER BY create_time DESC;
-- SELECT * FROM t_product WHERE name LIKE CONCAT('%', '手机', '%') AND category_id = 1 AND price BETWEEN 1000 AND 5000;
