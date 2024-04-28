CREATE TABLE `sys_table_field`
(
    `id`            bigint                                                       NOT NULL,
    `table_id`      bigint                                                        DEFAULT NULL COMMENT '父id',
    `title`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
    `name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '传参名字',
    `field`         varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
    `default_value` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '表名称',
    `type`          varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `verify`        varchar(255) COLLATE utf8mb4_general_ci                       DEFAULT NULL,
    `sort`          int                                                           DEFAULT NULL COMMENT '排序',
    `creator`       bigint                                                        DEFAULT NULL,
    `create_date`   datetime                                                      DEFAULT NULL,
    `updater`       bigint                                                        DEFAULT NULL,
    `update_date`   datetime                                                      DEFAULT NULL,
    `del_flag`      varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `table_id` (`table_id`,`field`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='页面菜单';