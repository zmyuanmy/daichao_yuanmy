
/*==============================================================*/
/* Table: messages                                              */
/*==============================================================*/
CREATE TABLE messages
(
   message_id           int not null AUTO_INCREMENT comment 'ID',
   from_user_id         int not null default 0 comment '发送方ID， 0则为系统发送',
   to_user_id         	int not null default 0 comment '接收方ID',
   creation_date        timestamp not null default 0,
   send_date            timestamp not null default 0,
   subject              varchar(250),
   message_type         int,
   message_text         mediumtext,
   is_sms               tinyint not null default 0,
   is_push              tinyint not null default 0,
   parameters           varchar(4000),
   is_read              tinyint not null default 0,
   is_hidden            tinyint not null default 0,
   PRIMARY KEY (message_id)
)
ENGINE InnoDB;
alter table messages add index i_messages_fuid (from_user_id);
alter table messages add index i_messages_tuid (to_user_id);
