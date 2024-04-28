CREATE TABLE `sys_table`
(
    `id`          bigint                                                       NOT NULL,
    `pid`         bigint                                                        DEFAULT NULL COMMENT '父id',
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
    `name`        bigint                                                        DEFAULT NULL COMMENT '用户数量',
    `vp`          varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '房间类型',
    `table_name`  varchar(30) COLLATE utf8mb4_general_ci                        DEFAULT NULL COMMENT '表名称',
    `creator`     bigint                                                        DEFAULT NULL,
    `create_date` datetime                                                      DEFAULT NULL,
    `updater`     bigint                                                        DEFAULT NULL,
    `update_date` datetime                                                      DEFAULT NULL,
    `del_flag`    varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   DEFAULT NULL COMMENT '删除标志',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `vp` (`vp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='页面菜单';