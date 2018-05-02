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
	`junlie` INT NULL,
	`budui` INT NULL,
	`youfu` INT NULL,
	`dibao` INT NULL,
	`suiqian` INT NULL,
	`liushou` INT NULL,
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
	`phone` VARCHAR(32) NULL,
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
	`id_type` VARCHAR(32) NULL,
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


DROP TABLE IF EXISTS `t_parameters`;
CREATE TABLE `t_parameters` (
	`id` INT NOT NULL PRIMARY KEY,
	`name` VARCHAR(128) NOT NULL,
	`description` VARCHAR(255) NULL,
	`value` TEXT NULL,
	CONSTRAINT `uid_parameters_name` UNIQUE INDEX(`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_optionitem`;
CREATE TABLE `t_optionitem` (
	`id` INT NOT NULL PRIMARY KEY,
	`parent_id` INT NULL,
	`com_key` VARCHAR(64) NOT NULL,
	`item_value` VARCHAR(255) NOT NULL,
	`item_text` VARCHAR(255) NOT NULL,
	`item_seq` INT NOT NULL,
	`item_selected` BOOLEAN NOT NULL,
	`validator` VARCHAR(255) NULL,
	INDEX `id_optionitem_parent_id` (`parent_id`),
	INDEX `id_optionitem_com_key` (`com_key`),
	INDEX `id_optionitem_item_value` (`item_value`),
	INDEX `id_optionitem_item_seq` (`item_seq`),
	CONSTRAINT `uid_optionitem_com_key_item_value` UNIQUE INDEX(`parent_id`, `com_key`, `item_value`),
	FOREIGN KEY(`parent_id`) REFERENCES `t_optionitem`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_option`;
DROP TABLE IF EXISTS `t_component`;
CREATE TABLE `t_component` (
	`id` INT NOT NULL PRIMARY KEY,
	`parent_id` INT NULL,
	`type` INT NOT NULL,
	`required` BOOLEAN NOT NULL,
	`keyword` VARCHAR(64) NOT NULL,
	`name` VARCHAR(64) NOT NULL,
	`description` VARCHAR(255) NULL,
	`width` INT NOT NULL,
	`height` INT NOT NULL,
	`validator` VARCHAR(255) NULL,
	INDEX `id_component_parent_id` (`parent_id`),
	CONSTRAINT `uid_component_keyword` UNIQUE INDEX(`keyword`),
	FOREIGN KEY(`parent_id`) REFERENCES `t_component`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_option` (
	`id` INT NOT NULL PRIMARY KEY,
	`parent_id` INT NULL,
	`component_id` INT NOT NULL,
	`text` VARCHAR(255) NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	`seq` INT NOT NULL,
	`selected` BOOLEAN NOT NULL,
	`validator` VARCHAR(255) NULL,
	INDEX `id_option_parent_id` (`parent_id`),
	INDEX `id_option_component_id` (`component_id`),
	INDEX `id_option_value` (`value`),
	INDEX `id_option_seq` (`seq`),
	CONSTRAINT `uid_option_component_parent_value` UNIQUE INDEX(`parent_id`, `component_id`, `value`),
	FOREIGN KEY(`component_id`) REFERENCES `t_component`(`id`),
	FOREIGN KEY(`parent_id`) REFERENCES `t_option`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

