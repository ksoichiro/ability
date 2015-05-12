create table if not exists `user` (
    `id` bigint(20) not null auto_increment,
    `username` varchar(255) not null,
    `manager_id` bigint(20),
    `password` varchar(255) not null,
    `enabled` boolean not null default true,
    `organization_id` bigint(20) not null,
    `role_id` bigint(20) not null,
    `updated_at` bigint(20) not null,
    `created_at` bigint(20) not null,
    primary key (`id`),
    unique (`username`)
) default charset=utf8;

create table if not exists `role` (
    `id` bigint(20) not null,
    `name` varchar(255) not null,
    primary key (`id`),
    unique (`name`)
) default charset=utf8;

create table if not exists `organization` (
    `id` bigint(20) not null auto_increment,
    `name` varchar(255) not null,
    primary key (`id`),
    unique (`name`)
) default charset=utf8;

create table if not exists `report` (
    `id` bigint(20) not null auto_increment,
    `title` varchar(255) not null,
    `submitted` boolean not null default false,
    `authorized` boolean not null default false,
    `creator_id` bigint(20),
    `authorizer_id` bigint(20),
    primary key (`id`)
) default charset=utf8;
