ALTER TABLE `disk`.`order` 
ADD COLUMN `status` varchar(20) NULL COMMENT '订单状态' AFTER `orderNo`;