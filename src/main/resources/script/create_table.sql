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
INSERT INTO jiastore.user_info (user_id, nickname, avatar, gender, phone, create_time, update_time)
VALUES (1, '管理员',
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F4d40b566-1f0a-4f8d-bc97-c513df8775b3%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1705040920&t=51a54bcb01c5fb4f3974ecb714e7f0bb',
        1, '624142800@qq.com', '2023-12-13 14:29:24', '2023-12-13 14:29:24');


drop table if exists role;
create table role
(
    id          int auto_increment comment '主键ID'
        primary key,
    name        varchar(25)            not null comment '角色名称',
    status      tinyint                not null comment '状态：0-失效；1-生效',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_name
        unique (name)
) comment '角色表';


drop table if exists user_role;
create table user_role
(
    id          int auto_increment comment '主键ID'
        primary key,
    user_id     int                    not null comment '用户ID',
    role_id     int                    not null comment '角色ID',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_user_id_role_id
        unique (user_id, role_id)
) comment '用户角色表';


drop table if exists authority;
create table authority
(
    id          int auto_increment comment '主键ID'
        primary key,
    auth_key    int                    not null comment '权限KEY',
    name        varchar(25)            not null comment '权限名称',
    type        varchar(25)            not null comment '权限类型',
    status      tinyint                not null comment '状态：0-失效；1-生效',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_auth_key
        unique (auth_key)
) comment '权限表';


drop table if exists role_authority;
create table role_authority
(
    id           int auto_increment comment '主键ID'
        primary key,
    role_id      int                    not null comment '角色ID',
    authority_id int                    not null comment '权限ID',
    create_time  datetime default now() not null comment '创建时间',
    update_time  datetime default now() not null comment '修改时间'
) comment '角色权限表';


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



drop table if exists menu;
create table menu
(
    id          int auto_increment comment '主键ID'
        primary key,
    name        varchar(25)            not null comment '菜单名称',
    status      tinyint                not null comment '状态：0-失效；1-生效',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_name
        unique (name)
) comment '菜单表';

drop table if exists user_menu;
create table user_menu
(
    id          int auto_increment comment '主键ID'
        primary key,
    user_id     int                    not null comment '用户ID',
    menu_id     int                    not null comment '菜单ID',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_user_id_menu_id
        unique (user_id, menu_id)
) comment '用户菜单表';

drop table if exists menu_authority;
create table menu_authority
(
    id           int auto_increment comment '主键ID'
        primary key,
    menu_id      int                    not null comment '菜单ID',
    authority_id int                    not null comment '权限ID',
    create_time  datetime default now() not null comment '创建时间',
    update_time  datetime default now() not null comment '修改时间'
) comment '菜单权限表';