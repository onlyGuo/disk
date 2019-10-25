ALTER TABLE `disk`.`order`
ADD COLUMN `shareFileId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分享ID' AFTER `subject`,
ADD COLUMN `sessionId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前连接ID(付款客户端)' AFTER `shareFileId`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`id`) USING BTREE;