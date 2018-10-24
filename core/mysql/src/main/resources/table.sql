CREATE TABLE `admin_user` (
  `id` varchar(20) NOT NULL,
  `password` varchar(64) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `del_yn` tinyint NOT NULL,
  `reg_date` datetime NOT NULL,
  `mod_date` datetime DEFAULT NULL,
  `email` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;