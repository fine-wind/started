SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `company_id` bigint NULL DEFAULT NULL,
  `del_flag` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dept_id`(`dept_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1274368172922916865, '测试角色', '测试角色', NULL, 1067246875800000001, '2020-06-20 23:47:04', 1067246875800000001, '2020-06-20 23:47:04', NULL, '0');
INSERT INTO `sys_role` VALUES (1421476705695756290, '普通人员', '普通人员', NULL, 1, '2021-07-31 22:23:51', 1, '2021-08-01 10:00:45', -1, '0');
INSERT INTO `sys_role` VALUES (1421651885717987330, '公司权限', '公司权限', NULL, 1, '2021-08-01 09:59:57', 1, '2021-08-01 09:59:57', -1, '0');

SET FOREIGN_KEY_CHECKS = 1;
