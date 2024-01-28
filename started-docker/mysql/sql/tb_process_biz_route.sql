SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_process_biz_route
-- ----------------------------
DROP TABLE IF EXISTS `tb_process_biz_route`;
CREATE TABLE `tb_process_biz_route`  (
  `id` bigint NOT NULL COMMENT 'id',
  `proc_def_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程定义ID',
  `biz_route` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务路由',
  `proc_def_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程定义KEY',
  `version` int NULL DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工作流业务路由' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_process_biz_route
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
