CREATE TABLE `disk`.`baidu_disk_link`  (
  `id` varchar(64) NOT NULL,
  `uid` bigint(0) NULL COMMENT '用户ID',
  `name` varchar(255) NULL COMMENT '文件名称或标识',
  `filePath` varchar(255) NULL COMMENT '文件在百度网盘中的路径',
  `link` varchar(255) NULL COMMENT '生成的直联连接',
  `form` varchar(100) NULL COMMENT '允许访问来源',
  `createTime` datetime(0) NULL COMMENT '创建时间',
  `updateTime` datetime(0) NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
);

ALTER TABLE `disk`.`baidu_disk_link`
ADD COLUMN `parentId` varchar(64) NULL AFTER `id`;

ALTER TABLE `disk`.`baidu_disk_link`
MODIFY COLUMN `link` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '生成的直联连接' AFTER `filePath`;