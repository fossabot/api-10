drop table if exists wechat_authy;
create table wechat_authy
(
  id varchar(255) not null primary key,
  open_id varchar(255) null,
  site varchar(255) null,
  account varchar(255) null,
  secret varchar(255) null,
  create_time datetime null,
  update_time datetime null
);

drop table if exists wechat_user;
create table wechat_user
(
  open_id varchar(255) not null primary key,
  session_key varchar(255) null,
  token varchar(255) null,
  update_time datetime null
);