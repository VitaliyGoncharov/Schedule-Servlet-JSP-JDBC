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
  FOREIGN KEY (`school`) REFERENCES `school` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `course_num` int(11) NOT NULL,
  `major` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`major`) REFERENCES `major` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
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
  `lesson_type` varchar(1),
  `classroom` varchar(5),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`subject`) REFERENCES `subject` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (`major`) REFERENCES `major` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT,
  FOREIGN KEY (`teacher`) REFERENCES `teacher` (`id`) ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8