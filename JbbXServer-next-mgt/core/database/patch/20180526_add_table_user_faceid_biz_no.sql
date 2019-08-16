CREATE TABLE `user_faceid_biz_no` (
  `user_id` int(10) not null,
  `biz_no` varchar(255)  not null,
  `random_number` varchar(10) ,
  `tokenrandomnumber` varchar(255), 
  `tokenvideo` varchar(255) ,
  `random_data` text ,
  `validatevideo_data` text,
  `verify_data` text ,
  PRIMARY KEY (`user_id`,`biz_no`)
) ENGINE=InnoDB ;