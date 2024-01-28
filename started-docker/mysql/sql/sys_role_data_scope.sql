SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope`  (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色数据权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------
INSERT INTO `sys_role_data_scope` VALUES (1274368179117903874, 1274368172922916865, 1067246875800000066, 1067246875800000001, '2020-06-20 23:47:05');
INSERT INTO `sys_role_data_scope` VALUES (1274368179231150082, 1274368172922916865, 1067246875800000064, 1067246875800000001, '2020-06-20 23:47:05');
INSERT INTO `sys_role_data_scope` VALUES (1274368179327619074, 1274368172922916865, 1067246875800000065, 1067246875800000001, '2020-06-20 23:47:05');
INSERT INTO `sys_role_data_scope` VALUES (1274368179432476674, 1274368172922916865, 1067246875800000067, 1067246875800000001, '2020-06-20 23:47:05');
INSERT INTO `sys_role_data_scope` VALUES (1274368179528945666, 1274368172922916865, 1067246875800000063, 1067246875800000001, '2020-06-20 23:47:05');
INSERT INTO `sys_role_data_scope` VALUES (1274368179625414657, 1274368172922916865, 1067246875800000068, 1067246875800000001, '2020-06-20 23:47:05');
INSERT INTO `sys_role_data_scope` VALUES (1274368179730272258, 1274368172922916865, 1067246875800000062, 1067246875800000001, '2020-06-20 23:47:05');

SET FOREIGN_KEY_CHECKS = 1;
