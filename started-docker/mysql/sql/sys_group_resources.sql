SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_group_resources
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_resources`;
CREATE TABLE `sys_group_resources`  (
  `id` bigint NOT NULL COMMENT 'id',
  `group_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `resources_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `del_flag` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`group_id` ASC) USING BTREE,
  INDEX `idx_menu_id`(`resources_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关系' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
