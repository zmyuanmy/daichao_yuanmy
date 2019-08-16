CREATE TABLE `iou_photos` (
  `id` int(11) not null AUTO_INCREMENT,
  `iou_code` varchar(50),
  `user_id` int(11) not null,
  `file_name` varchar(30) ,
  `upload_date` timestamp not null default CURRENT_TIMESTAMP,
  `deleted` tinyint(4) not null default 0,
  PRIMARY KEY (`id`)
) ENGINE InnoDB;
