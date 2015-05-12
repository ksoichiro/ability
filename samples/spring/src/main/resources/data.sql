truncate table `user`;
truncate table `role`;
truncate table `organization`;
truncate table `report`;

insert into `role` (`id`, `name`) values (1, 'ROLE_ADMIN');
insert into `role` (`id`, `name`) values (2, 'ROLE_USER');

insert into `user` (`id`, `username`, `manager_id`, `organization_id`, `password`, `enabled`, `role_id`, `created_at`, `updated_at`) values (1, 'admin', null, 1, 'password', true, 1, 0, 0);
insert into `user` (`id`, `username`, `manager_id`, `organization_id`, `password`, `enabled`, `role_id`, `created_at`, `updated_at`) values (2, 'user1', null, 2, 'password', true, 2, 0, 0);
insert into `user` (`id`, `username`, `manager_id`, `organization_id`, `password`, `enabled`, `role_id`, `created_at`, `updated_at`) values (3, 'user2', 2,    2, 'password', true, 2, 0, 0);
insert into `user` (`id`, `username`, `manager_id`, `organization_id`, `password`, `enabled`, `role_id`, `created_at`, `updated_at`) values (4, 'user3', null, 3, 'password', true, 2, 0, 0);

insert into `organization` (`name`) values ('A');
insert into `organization` (`name`) values ('B');
insert into `organization` (`name`) values ('C');
insert into `organization` (`name`) values ('D');

insert into `report` (`title`, `submitted`, `authorized`, `creator_id`, `authorizer_id`) values ('test1', false, false, 2, null);
insert into `report` (`title`, `submitted`, `authorized`, `creator_id`, `authorizer_id`) values ('test2', false, false, 2, null);
insert into `report` (`title`, `submitted`, `authorized`, `creator_id`, `authorizer_id`) values ('test3', true, true, 3, 2);
insert into `report` (`title`, `submitted`, `authorized`, `creator_id`, `authorizer_id`) values ('test4', true, false, 4, null);
