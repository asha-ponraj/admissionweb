DROP TABLE IF EXISTS `t_versions`;
CREATE TABLE `t_versions` ( `version` VARCHAR(32) NOT NULL PRIMARY KEY ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `t_versions` VALUES ('1.000');

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
	`id` INT NOT NULL PRIMARY KEY,
	`username` VARCHAR(16) NOT NULL UNIQUE,
	`password` VARCHAR(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `t_user` (`id`, `username`, `password`) VALUES(1, 'admin', 'config');

DROP TABLE IF EXISTS `t_district`;
DROP TABLE IF EXISTS `t_city`;
DROP TABLE IF EXISTS `t_province`;
CREATE TABLE `t_province` (
	`id` INT NOT NULL PRIMARY KEY, 
	`name` VARCHAR(64) NOT NULL,
	CONSTRAINT `uid_province_name` UNIQUE INDEX(`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_city` (
	`id` INT NOT NULL PRIMARY KEY,
	`province_id` INT NOT NULL,
	`name` VARCHAR(64) NOT NULL,
	CONSTRAINT `uid_city_province_id_name` UNIQUE INDEX(`province_id`, `name`),
	INDEX `id_city_province_id` (`province_id`),
	INDEX `id_city_name` (`name`),
	FOREIGN KEY(`province_id`) REFERENCES `t_province`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_district` (
	`id` INT NOT NULL PRIMARY KEY,
	`city_id` INT NOT NULL,
	`name` VARCHAR(64) NOT NULL,
	CONSTRAINT `uid_district_city_id_name` UNIQUE INDEX(`city_id`, `name`),
	INDEX `id_district_city_id` (`city_id`),
	INDEX `id_district_name` (`name`),
	FOREIGN KEY(`city_id`) REFERENCES `t_city`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `t_familymember`;
DROP TABLE IF EXISTS `t_address`;
DROP TABLE IF EXISTS `t_application`;

CREATE TABLE `t_application` (
	`id` INT NOT NULL PRIMARY KEY,
	`subbarcode` VARCHAR(4) NOT NULL,
	`username` VARCHAR(64) NOT NULL,
	`password` VARCHAR(64) NOT NULL,
	`grade` INT NOT NULL,
	`candidate_type` INT NOT NULL,
	`name` VARCHAR(32) NOT NULL,
	`former_name` VARCHAR(32) NULL,
	`gender` VARCHAR(8) NOT NULL,
	`jiguan` VARCHAR(32) NULL,
	`ethnicity` VARCHAR(32) NULL,
	`nation` VARCHAR(32) NOT NULL,
	`birthday` DATE NOT NULL,
	`has_birthcert` BOOLEAN NOT NULL,
	`birth_place` VARCHAR(32) NOT NULL,
	`only_child` BOOLEAN NOT NULL,
	`went_kindergarten` BOOLEAN NOT NULL,
	`nursery` VARCHAR(64) NULL,
	`pid_type` INT NOT NULL,
	`pid_number` VARCHAR(64) NOT NULL,
	`hk_nature` INT NOT NULL,
	`hk_type` INT NOT NULL,
	`hk_regdate` DATETIME NULL,
	`property_nature` INT NOT NULL,
	`property_number` VARCHAR(64) NULL,
	`property_regdate` DATETIME NULL,
	`property_other` VARCHAR(128) NULL,
	`property_type` INT NOT NULL,
	`health` VARCHAR(32) NOT NULL,
	`allergic` BOOLEAN NOT NULL,
	`specific_disease` VARCHAR(32) NULL,
	`immunity_cert` BOOLEAN NOT NULL,
	`remark` TEXT NOT NULL,
	`create_time` DATETIME NOT NULL,
	`status` INT NOT NULL,
	`accept_time` DATETIME NULL,
	`notify_time` DATETIME NULL,
	`notify` VARCHAR(255) NULL,
	`download_time` DATETIME NULL,
	`checkin_time` DATETIME NULL,
	`recheckin` INT NOT NULL,
	INDEX `id_application_name` (`name`),
	INDEX `id_application_pid_type` (`pid_type`),
	INDEX `id_application_pid_number` (`pid_number`),
	INDEX `id_application_create_time` (`create_time`),
	INDEX `id_application_status` (`status`),
	INDEX `id_application_accept_time` (`accept_time`),
	INDEX `id_application_notify_time` (`notify_time`),
	INDEX `id_application_checkin_time` (`checkin_time`),
	INDEX `id_application_recheckin` (`recheckin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_address` (
	`id` INT NOT NULL PRIMARY KEY,
	`application_id` INT NOT NULL,
	`type` INT NOT NULL,
	`content` VARCHAR(128) NULL,
	`town` VARCHAR(64) NULL,
	`resident_council` VARCHAR(32) NULL,
	`postcode` VARCHAR(8) NULL,
	CONSTRAINT `uid_address_application_id_type` UNIQUE INDEX(`application_id`, `type`),
	INDEX `id_address_application_id` (`application_id`),
	FOREIGN KEY(`application_id`) REFERENCES `t_application`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `t_familymember` (
	`id` INT NOT NULL PRIMARY KEY,
	`application_id` INT NOT NULL,
	`type` INT NOT NULL,
	`name` VARCHAR(32) NOT NULL,
	`education` VARCHAR(32) NULL,
	`id_number` VARCHAR(32) null,
	`company` VARCHAR(255) NULL,
	`resident_permit` VARCHAR(255) NULL,
	`position` VARCHAR(64) NULL,
	`phone` VARCHAR(32) NULL,
	`mobile` VARCHAR(32) NULL,
	CONSTRAINT `uid_familymember_application_id_type` UNIQUE INDEX(`application_id`, `type`),
	INDEX `id_familymember_application_id` (`application_id`),
	FOREIGN KEY(`application_id`) REFERENCES `t_application`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
	`id` INT NOT NULL PRIMARY KEY,
	`title` VARCHAR(255) NOT NULL,
	`content` TEXT NOT NULL,
	`top` TINYINT(1) NOT NULL,
	`create_time` DATETIME NOT NULL,
	`top_time` DATETIME NULL,
	`seq` INT NOT NULL,
	CONSTRAINT `uid_news_title` UNIQUE INDEX(`title`),
	INDEX `id_news_top` (`top`),
	INDEX `id_news_create_time` (`create_time`),
	INDEX `id_news_top_time` (`top_time`),
	INDEX `id_news_seq` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

