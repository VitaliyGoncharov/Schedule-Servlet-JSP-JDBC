CREATE TABLE IF NOT EXISTS `school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `url` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `major` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `duration` int(11) NOT NULL,
  `school` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `major_school_idx` (`school`),
  CONSTRAINT `major_school` FOREIGN KEY (`school`) REFERENCES `school` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `course_num` int(11) NOT NULL,
  `major` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_major` (`major`),
  CONSTRAINT `groups_ibfk_1` FOREIGN KEY (`major`) REFERENCES `major` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `order_num` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `teacher` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`firstname` varchar(50) NOT NULL,
	`lastname` varchar(50) NOT NULL,
	`middlename` varchar(50) NOT NULL,
	`mail` varchar(50) NOT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` int(11) NOT NULL,
  `day_num` int(11) NOT NULL,
  `week` varchar(4) NOT NULL,
  `subject_num` int(11) NOT NULL,
  `major` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `teacher` int(11),
  `lesson_type` varchar(1) NOT NULL,
  `classroom` varchar(5),
  PRIMARY KEY (`id`),
  KEY `fk_subject` (`subject`),
  KEY `fk_major` (`major`),
  KEY `fk_group_id` (`group_id`),
  KEY `fk_teacher` (`teacher`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`subject`) REFERENCES `subject` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`major`) REFERENCES `major` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_3` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `schedule_ibfk_4` FOREIGN KEY (`teacher`) REFERENCES `teacher` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8