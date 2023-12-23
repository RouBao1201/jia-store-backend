-- auto-generated definition
drop table if exists user;
create table user
(
    id          int auto_increment comment '主键ID'
        primary key,
    username    varchar(200)           not null comment '用户名',
    password    varchar(200)           not null comment '密码',
    status      tinyint                not null comment '状态：0-失效；1-生效',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_username
        unique (username)
) comment '用户表';
INSERT INTO user (id, username, password, status, create_time, update_time)
VALUES (1, 'admin', '297b738b4495bb9ea1972ed24db31ab9', 1, now(), now());



drop table if exists user_info;
create table user_info
(
    user_id     int comment '主键ID'
        primary key,
    nickname    varchar(30)            not null comment '用户昵称',
    avatar      varchar(500)           not null comment '用户头像',
    gender      tinyint                not null comment '用户性别：0-女；1-男',
    email       varchar(20)            not null comment '用户邮箱',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间'
) comment '用户信息表';
INSERT INTO user_info (user_id, nickname, avatar, gender, email, create_time, update_time)
VALUES (1, '管理员',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F4d40b566-1f0a-4f8d-bc97-c513df8775b3%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1705040920&t=51a54bcb01c5fb4f3974ecb714e7f0bb',
        1, '624142800@qq.com', '2023-12-13 14:29:24', '2023-12-13 14:29:24');



drop table if exists dict_config;
create table dict_config
(
    id          serial                             not null comment '主键ID'
        primary key,
    dict_key    varchar(25)                        not null comment '字典KEY',
    label       varchar(25)                        not null comment '字典标签',
    value       varchar(50)                        not null comment '字典标签值',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null comment '修改时间',
    unique (dict_key, label, value)
) comment '字典配置表';
INSERT INTO dict_config (id, dict_key, label, value, create_time, update_time)
VALUES (12, 'GENDER', '男', '1', '2023-12-20 13:07:55', '2023-12-20 13:07:55');
INSERT INTO dict_config (id, dict_key, label, value, create_time, update_time)
VALUES (13, 'GENDER', '女', '0', '2023-12-20 13:07:55', '2023-12-20 13:07:55');
INSERT INTO dict_config (id, dict_key, label, value, create_time, update_time)
VALUES (14, 'STATUS', '启动', '1', '2023-12-21 11:41:50', '2023-12-21 11:41:50');
INSERT INTO dict_config (id, dict_key, label, value, create_time, update_time)
VALUES (15, 'STATUS', '停用', '0', '2023-12-21 11:42:05', '2023-12-21 11:42:05');