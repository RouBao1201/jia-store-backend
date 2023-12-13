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
    avatar      varchar(500)           not null comment '密码',
    gender      tinyint                not null comment '用户性别：0-女；1-男',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间'
) comment '用户信息表';


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
    status       tinyint                not null comment '状态：0-失效；1-生效',
    create_time  datetime default now() not null comment '创建时间',
    update_time  datetime default now() not null comment '修改时间'
) comment '角色权限表';