CREATE TABLE IF NOT EXISTS `delivery`.`store` (
 `id` bigint NOT NULL AUTO_INCREMENT,
 `name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
 `address` varchar(150) COLLATE utf8mb4_bin NOT NULL,
 `status` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
 `category` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
 `star` double DEFAULT NULL,
 `thumbnail_url` varchar(200) COLLATE utf8mb4_bin NOT NULL,
 `minimum_amount` decimal(11,4) NOT NULL,
 `minimum_delivery_amount` decimal(11,4) NOT NULL,
 `phone_number` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;


CREATE TABLE IF NOT EXISTS `delivery`.`store_menu` (
 `id` BIGINT(32) NOT NULL AUTO_INCREMENT,
 `store_id` BIGINT(32) NOT NULL,
 `name` VARCHAR(100) NOT NULL,
 `amount` DECIMAL(11,4) NOT NULL,
 `status` VARCHAR(50) NOT NULL,
 `thumbnail_url` VARCHAR(200) NOT NULL,
 `like_count` INT NULL DEFAULT 0,
 `sequence` INT NULL DEFAULT 0,
PRIMARY KEY (`id`)) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS `delivery`.`store_user` (
  `id` bigint(32) NOT NULL,
  `store_id` bigint(32) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  `registered_at` datetime DEFAULT NULL,
  `unregistered_at` datetime DEFAULT NULL,
  `last_login_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS `delivery`.`user` (
 `id` bigint NOT NULL AUTO_INCREMENT,
 `name` varchar(50) COLLATE utf8mb4_bin NOT NULL,
 `email` varchar(100) COLLATE utf8mb4_bin NOT NULL,
 `password` varchar(100) COLLATE utf8mb4_bin NOT NULL,
 `status` varchar(50) COLLATE utf8mb4_bin NOT NULL,
 `address` varchar(150) COLLATE utf8mb4_bin NOT NULL,
 `registered_at` datetime DEFAULT NULL,
 `unregistered_at` datetime DEFAULT NULL,
 `last_login_at` datetime DEFAULT NULL,
PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS `delivery`.`user_order` (
 `id` BIGINT(32) NOT NULL AUTO_INCREMENT,
 `user_id` BIGINT(32) NOT NULL,
 `status` VARCHAR(50) NOT NULL,
 `amount` DECIMAL(11,4) NOT NULL,
 `ordered_at` DATETIME NULL,
 `accepted_at` DATETIME NULL,
 `cooking_started_at` DATETIME NULL,
 `delivery_started_at` DATETIME NULL,
 `received_at` DATETIME NULL,
PRIMARY KEY (`id`),
INDEX `idx_user_id` (`user_id` ASC) VISIBLE
) ENGINE = InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE IF NOT EXISTS `delivery`.`user_order_menu` (
 `id` bigint NOT NULL AUTO_INCREMENT,
 `user_order_id` bigint NOT NULL,
 `store_menu_id` bigint NOT NULL,
 `status` varchar(50) COLLATE utf8mb4_bin NOT NULL,
PRIMARY KEY (`id`),
    KEY `idx_user_order_id` (`user_order_id`),
    KEY `idx_store_menu_id` (`store_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
