SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `head_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `Multiple_login` tinyint UNSIGNED NULL DEFAULT 0 COMMENT ' 多处登录   0：否   1：是',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `id_card` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `del_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE,
  UNIQUE INDEX `uk_email`(`email` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin@hefang.group', '$e0801$EGrfGqJc85FvyE7IlltAvF85qnKNuk1e65qfxIsRwDWEaoncIULETKyMFWiaoTWX6mSKb3F3RLespDPKCp0uvg==$8HK4b2q7YXmb48kLDjubKMtZNqFwYTmHN7wU5VJBkP8=', 'admin@hefang.group', '管理员昵称', NULL, NULL, NULL, NULL, 1, 1, 0, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_user` VALUES (1492317946579197954, '123@hefang.group', '$e0801$EGrfGqJc85FvyE7IlltAvF85qnKNuk1e65qfxIsRwDWEaoncIULETKyMFWiaoTWX6mSKb3F3RLespDPKCp0uvg==$8HK4b2q7YXmb48kLDjubKMtZNqFwYTmHN7wU5VJBkP8=', '123@hefang.group', '123@hefang.group', NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_user` VALUES (1494929410638610434, 'ekjlks', '$e0801$WYxOsQXD45k24x1D9Q6h5GmdnycilcnO6HyAzEUF//0ogp4q+oTP4WuvbTzU61LhFh8TIDDE45o0pzuXXXYdUw==$sF39+uRKbXEFlp39hHE/+QgJpv0p+9HvGsVb72TBxgg=', 'jlsei@lo.com', 'jlsei@lo.com', NULL, NULL, NULL, NULL, 0, NULL, 0, NULL, NULL, NULL, NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
