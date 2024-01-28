SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_base_conf
-- ----------------------------
DROP TABLE IF EXISTS `sys_base_conf`;
CREATE TABLE `sys_base_conf`  (
  `id` bigint NOT NULL,
  `item` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_date` datetime NULL DEFAULT NULL,
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-基础默认配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_base_conf
-- ----------------------------
INSERT INTO `sys_base_conf` VALUES (1, 'contribution', '贡献量', '0', 0, NULL, NULL, NULL, NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
