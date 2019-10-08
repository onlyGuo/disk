ALTER TABLE `disk`.`order` 
MODIFY COLUMN `orderNo` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号' AFTER `uid`;