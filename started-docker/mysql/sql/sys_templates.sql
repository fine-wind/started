SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_templates
-- ----------------------------
DROP TABLE IF EXISTS `sys_templates`;
CREATE TABLE `sys_templates`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'name',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'path',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '系统-页面模板' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_templates
-- ----------------------------
INSERT INTO `sys_templates` VALUES (10000, '基础模板', NULL, NULL);
INSERT INTO `sys_templates` VALUES (10001, '常见模板', 'templates/t1/index', NULL);

SET FOREIGN_KEY_CHECKS = 1;
