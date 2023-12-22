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
INSERT INTO role (id, name, status, create_time, update_time)
VALUES (0, 'SuperAdmin', 1, now(), now());


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


drop table if exists authority;
create table authority
(
    id          int auto_increment comment '主键ID'
        primary key,
    auth_key    varchar(25)                    not null comment '权限KEY',
    name        varchar(25)            not null comment '权限名称',
    type        varchar(25)            not null comment '权限类型',
    status      tinyint                not null comment '状态：0-失效；1-生效',
    create_time datetime default now() not null comment '创建时间',
    update_time datetime default now() not null comment '修改时间',
    constraint unique_auth_key
        unique (auth_key)
) comment '权限表';