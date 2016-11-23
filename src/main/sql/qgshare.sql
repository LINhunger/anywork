/*
Navicat MySQL Data Transfer

Source Server         : Mysql
Source Server Version : 50536
Source Host           : 127.0.0.1:3306
Source Database       : qgshare

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2016-11-18 23:44:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `apply`
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `applyId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `senderId` int(10) NOT NULL,
  `organId` int(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`applyId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apply
-- ----------------------------

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `commentId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `senderId` int(10) NOT NULL,
  `receiverId` int(10) NOT NULL,
  `targetId` int(10) NOT NULL,
  `type` int(1) NOT NULL,
  `content` varchar(100) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `aCommentId` int(10) DEFAULT NULL COMMENT '这是回复所对应评论的id',
  PRIMARY KEY (`commentId`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '1', '0', '1', '0', '厉害了，我的歌', '2016-11-15 19:53:08', null);
INSERT INTO `comment` VALUES ('2', '2', '0', '1', '0', '厉害了，我的肾', '2016-11-15 19:54:41', null);
INSERT INTO `comment` VALUES ('3', '1', '2', '1', '0', '大神好厉害', '2016-11-15 19:55:27', '2');
INSERT INTO `comment` VALUES ('4', '2', '1', '1', '0', '还是你厉害', '2016-11-15 19:55:48', '3');
INSERT INTO `comment` VALUES ('8', '1', '0', '1', '0', 'dsfsdf', '2016-11-17 03:01:14', null);
INSERT INTO `comment` VALUES ('9', '1', '0', '1', '0', 'dsfsdfdsfsafd', '2016-11-17 03:01:38', null);
INSERT INTO `comment` VALUES ('10', '1', '0', '1', '1', 'dsfgsdg', '2016-11-17 03:19:08', null);
INSERT INTO `comment` VALUES ('11', '1', '0', '1', '1', 'fgdfg', '2016-11-17 03:38:03', null);
INSERT INTO `comment` VALUES ('12', '1', '0', '1', '1', '12345678', '2016-11-17 03:40:03', null);
INSERT INTO `comment` VALUES ('13', '1', '0', '1', '1', '1234567', '2016-11-17 03:44:58', null);
INSERT INTO `comment` VALUES ('14', '1', '0', '1', '1', 'sdfsfsdf', '2016-11-17 03:47:14', null);
INSERT INTO `comment` VALUES ('15', '1', '0', '1', '1', '1234567890987654321', '2016-11-17 03:53:49', null);
INSERT INTO `comment` VALUES ('16', '1', '4', '1', '3', '12345678900987654321', '2016-11-17 03:54:12', '15');
INSERT INTO `comment` VALUES ('17', '1', '4', '1', '3', 'sdfsafd', '2016-11-17 04:02:59', '15');
INSERT INTO `comment` VALUES ('18', '1', '2', '1', '3', 'sdfsdfad', '2016-11-17 04:05:48', '15');
INSERT INTO `comment` VALUES ('19', '1', '2', '1', '3', 'sdfsdfsdf', '2016-11-17 04:06:05', '15');
INSERT INTO `comment` VALUES ('20', '1', '2', '1', '3', 'sdfsdfsdf', '2016-11-17 04:06:07', '15');
INSERT INTO `comment` VALUES ('21', '1', '2', '1', '3', '1', '2016-11-17 04:14:57', '15');
INSERT INTO `comment` VALUES ('22', '1', '4', '1', '3', '123', '2016-11-17 04:17:03', '10');
INSERT INTO `comment` VALUES ('23', '1', '4', '1', '3', '123', '2016-11-17 04:17:04', '10');
INSERT INTO `comment` VALUES ('24', '1', '4', '1', '3', '123', '2016-11-17 04:17:04', '10');
INSERT INTO `comment` VALUES ('25', '1', '4', '1', '3', '123', '2016-11-17 04:17:04', '10');
INSERT INTO `comment` VALUES ('26', '1', '4', '1', '3', '123', '2016-11-17 04:17:05', '10');
INSERT INTO `comment` VALUES ('27', '1', '4', '1', '3', '456', '2016-11-17 04:17:24', '10');
INSERT INTO `comment` VALUES ('28', '1', '4', '1', '3', '789', '2016-11-17 04:17:39', '10');
INSERT INTO `comment` VALUES ('29', '1', '2', '1', '3', '3', '2016-11-17 04:17:59', '15');
INSERT INTO `comment` VALUES ('47', '1', '0', '2', '1', '1234567890', '2016-11-17 08:11:26', null);
INSERT INTO `comment` VALUES ('48', '2', '1', '2', '3', '大神好厉害', '2016-11-17 08:13:26', '47');
INSERT INTO `comment` VALUES ('49', '1', '1', '2', '3', '还是你厉害', '2016-11-17 08:14:23', '47');
INSERT INTO `comment` VALUES ('50', '2', '1', '2', '3', '你也很厉害', '2016-11-17 08:15:11', '47');
INSERT INTO `comment` VALUES ('51', '2', '0', '2', '1', '厉害了，我的哥', '2016-11-17 08:15:44', null);
INSERT INTO `comment` VALUES ('52', '1', '2', '2', '3', 'aaaa', '2016-11-17 15:28:51', '51');
INSERT INTO `comment` VALUES ('53', '1', '2', '2', '3', 'aaaa', '2016-11-17 15:28:51', '51');
INSERT INTO `comment` VALUES ('54', '1', '2', '2', '3', 'aaaa', '2016-11-17 15:28:52', '51');
INSERT INTO `comment` VALUES ('55', '1', '2', '2', '3', 'aaaa', '2016-11-17 15:28:52', '51');
INSERT INTO `comment` VALUES ('56', '1', '2', '2', '3', 'aaaa', '2016-11-17 15:28:52', '51');
INSERT INTO `comment` VALUES ('57', '2', '2', '2', '3', '你更厉害', '2016-11-17 15:35:48', '51');
INSERT INTO `comment` VALUES ('58', '1', '0', '4', '1', '1111', '2016-11-17 16:26:49', null);

-- ----------------------------
-- Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `gradeId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `homeworkId` int(10) NOT NULL,
  `answerId` int(10) NOT NULL,
  `userId` int(10) NOT NULL,
  `grade` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`gradeId`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('76', '1', '6', '1', '5');
INSERT INTO `grade` VALUES ('77', '1', '1', '2', '5');
INSERT INTO `grade` VALUES ('78', '1', '1', '1', '4');
INSERT INTO `grade` VALUES ('79', '1', '8', '1', '5');

-- ----------------------------
-- Table structure for `hanswer`
-- ----------------------------
DROP TABLE IF EXISTS `hanswer`;
CREATE TABLE `hanswer` (
  `hAnswerId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `homeworkId` int(10) NOT NULL,
  `authorId` int(10) NOT NULL,
  `mark` text,
  `grade5` int(1) NOT NULL DEFAULT '0',
  `grade4` int(1) NOT NULL DEFAULT '0',
  `grade3` int(1) NOT NULL DEFAULT '0',
  `note` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`hAnswerId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hanswer
-- ----------------------------
INSERT INTO `hanswer` VALUES ('1', '1', '2', '12345ty', '1', '1', '0', '123456', '2016-11-15 19:39:38');
INSERT INTO `hanswer` VALUES ('2', '1', '3', '抱歉我不会饿\n# asdasd\n![小花.jpg](https://localhost:80/anywork/upload/1479321749215.jpeg)', '0', '0', '0', ' ', '2016-11-15 19:41:26');
INSERT INTO `hanswer` VALUES ('6', '1', '1', '林寒戈', '1', '0', '0', '', '2016-11-17 02:47:17');
INSERT INTO `hanswer` VALUES ('7', '2', '1', '我是林寒戈，是广东熬夜大学的一名学生', '0', '0', '0', '', '2016-11-17 02:50:52');
INSERT INTO `hanswer` VALUES ('8', '1', '4', '1234567890\n# 123456\n## 1234567', '1', '0', '0', '', '2016-11-17 04:26:17');
INSERT INTO `hanswer` VALUES ('9', '3', '1', '1.A\n2.#####', '0', '0', '0', '', '2016-11-17 08:56:37');
INSERT INTO `hanswer` VALUES ('10', '3', '2', '测试1\n![123.jpg](https://localhost:80/anywork/upload/1479403245372.jpeg)', '0', '0', '0', '', '2016-11-18 01:20:50');
INSERT INTO `hanswer` VALUES ('11', '22', '1', '# asdas\n## asdasd\n![小狗.jpg](https://localhost:80/anywork/upload/1479466546591.jpeg)', '0', '0', '0', '', '2016-11-18 18:56:09');
INSERT INTO `hanswer` VALUES ('12', '21', '1', '# return ture；\n\n![小狗.jpg](https://localhost:80/anywork/upload/1479467527684.jpeg)', '0', '0', '0', '', '2016-11-18 19:12:14');

-- ----------------------------
-- Table structure for `homework`
-- ----------------------------
DROP TABLE IF EXISTS `homework`;
CREATE TABLE `homework` (
  `homeworkId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `organId` int(10) NOT NULL,
  `homeworkTitle` varchar(20) NOT NULL,
  `mark` text NOT NULL,
  `authorId` int(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `endingTime` datetime NOT NULL,
  `submitCount` int(3) NOT NULL DEFAULT '0',
  `status` text NOT NULL,
  PRIMARY KEY (`homeworkId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', '1', '1离散数学', '设R和S是A上的任意关系，若R和S是传递的，则RUS是传递的。这个结论是否成立？', '2', '2016-11-14 19:27:25', '2016-11-16 19:27:11', '2', '&&1&4');
INSERT INTO `homework` VALUES ('2', '1', '2程序设计', '有1,2,3,4，4个数字，能组成多少个互不相同的无重复数字的三位数？', '2', '2016-11-14 19:29:49', '2016-11-16 19:29:45', '4', '&1');
INSERT INTO `homework` VALUES ('3', '1', '3数据结构', '1.数据具有意义的最小单位是______。A.元素	B.结点	C.数据类型	D.数据项\r\n2.请用自己的话描述什么数据结构？', '2', '2016-11-12 19:33:11', '2016-11-20 19:33:02', '2', '&&1&2');
INSERT INTO `homework` VALUES ('4', '1', ' 发作业测试', ' 发作业测试', '1', '2016-11-17 16:26:04', '2016-11-17 00:00:00', '0', '');
INSERT INTO `homework` VALUES ('19', '1', '微信测试', '微信测试', '1', '2016-11-17 22:23:01', '2016-12-17 00:00:00', '0', '');
INSERT INTO `homework` VALUES ('20', '1', '微信公告测试', '微信公告测试', '1', '2016-11-18 08:07:00', '2016-12-12 00:00:00', '0', '');
INSERT INTO `homework` VALUES ('21', '1', '作业测试', '# 作业\n**测试**', '1', '2016-11-18 16:47:21', '2016-11-21 00:00:00', '1', '&1');
INSERT INTO `homework` VALUES ('22', '1', '作业测试', '# 作业\n**测试**', '1', '2016-11-18 16:48:29', '2016-11-21 00:00:00', '1', '&1');
INSERT INTO `homework` VALUES ('23', '1', '作业测试 ', '# 作业\n**测试**', '1', '2016-11-18 16:49:50', '2016-11-19 00:00:00', '0', '');
INSERT INTO `homework` VALUES ('24', '1', '作业', '1234567', '1', '2016-11-18 16:52:34', '2016-11-19 00:00:00', '0', '');
INSERT INTO `homework` VALUES ('25', '1', '作文：我爱冰淇淋', '我爱冰淇淋我爱冰淇淋', '1', '2016-11-18 20:36:49', '2016-11-19 00:00:00', '0', '');

-- ----------------------------
-- Table structure for `inform`
-- ----------------------------
DROP TABLE IF EXISTS `inform`;
CREATE TABLE `inform` (
  `informId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `authorId` int(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `informTitle` varchar(30) NOT NULL,
  `mark` text NOT NULL,
  `organId` int(10) NOT NULL,
  PRIMARY KEY (`informId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inform
-- ----------------------------
INSERT INTO `inform` VALUES ('1', '2', '2016-11-15 19:05:53', '考试通知', '本学期《数据结构》其中考将于11.13，教一402进行，进同学们及时复习前三章的全部内容！！！', '1');
INSERT INTO `inform` VALUES ('2', '2', '2016-11-14 21:37:56', '4体侧通知', '明天体侧', '1');
INSERT INTO `inform` VALUES ('3', '2', '2016-11-14 21:38:30', '5放假通知', '骗你的啦', '1');
INSERT INTO `inform` VALUES ('4', '1', '2016-11-17 15:27:35', 'asdsadsada', 'asdasd', '1');
INSERT INTO `inform` VALUES ('5', '1', '2016-11-17 15:49:19', '林寒戈粉丝团', '林寒戈粉丝团', '1');
INSERT INTO `inform` VALUES ('6', '1', '2016-11-18 08:06:40', '微信公告测试', '微信公告测试', '1');
INSERT INTO `inform` VALUES ('7', '1', '2016-11-18 12:36:56', 'my测试', '# my测试', '1');
INSERT INTO `inform` VALUES ('8', '1', '2016-11-18 13:42:19', 'weixin', '234567890', '1');
INSERT INTO `inform` VALUES ('9', '1', '2016-11-18 13:44:01', '微信', '测试', '1');
INSERT INTO `inform` VALUES ('10', '1', '2016-11-18 13:46:18', '微信测试', '# 微信测试', '1');
INSERT INTO `inform` VALUES ('11', '1', '2016-11-18 13:47:52', '微信测试2', '# 微信测试2', '1');
INSERT INTO `inform` VALUES ('12', '1', '2016-11-18 13:50:30', '微信测试2', '# 微信测试2', '1');
INSERT INTO `inform` VALUES ('13', '1', '2016-11-18 16:07:15', '微信测试', '微信测试', '1');
INSERT INTO `inform` VALUES ('14', '1', '2016-11-18 16:37:40', '微信测试', '# 微信测试', '1');
INSERT INTO `inform` VALUES ('15', '1', '2016-11-18 20:19:31', 'eff', 'dfdf', '1');
INSERT INTO `inform` VALUES ('16', '1', '2016-11-18 20:30:48', 'eff', 'dfdf', '1');
INSERT INTO `inform` VALUES ('17', '1', '2016-11-18 20:31:02', 'eff', 'dfdf', '1');

-- ----------------------------
-- Table structure for `organization`
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `organId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `organName` varchar(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(100) NOT NULL DEFAULT '',
  `organCount` int(3) NOT NULL DEFAULT '0',
  PRIMARY KEY (`organId`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('1', '15网络一班', '2016-11-15 19:14:35', '这是萌萌哒的一班', '33');
INSERT INTO `organization` VALUES ('2', '15计科二班', '2016-11-06 10:58:18', '这是一直很饿的二班', '2');
INSERT INTO `organization` VALUES ('3', '15计科三班', '2016-11-06 11:50:58', '这是很饥渴的三班', '1');
INSERT INTO `organization` VALUES ('4', '15软件四班', '2016-11-15 19:07:27', '这是软软的四班', '1');
INSERT INTO `organization` VALUES ('15', '林寒戈粉丝团', '2016-11-16 19:12:17', '然而这个群只有我一个人', '0');
INSERT INTO `organization` VALUES ('16', '软件工程五班', '2016-11-16 22:35:54', '软件工程五班', '0');
INSERT INTO `organization` VALUES ('23', '软件十班', '2016-11-18 03:05:39', '软件十班', '1');
INSERT INTO `organization` VALUES ('24', '寒戈按摩小分队', '2016-11-18 21:46:33', '专门为前端大哥按摩服务的小分队', '1');
INSERT INTO `organization` VALUES ('25', '寒戈按摩小分队', '2016-11-18 21:46:39', '专门为前端大哥按摩服务的小分队', '1');
INSERT INTO `organization` VALUES ('26', '寒戈小分队2', '2016-11-18 21:47:54', '不只是为一个人服务', '1');

-- ----------------------------
-- Table structure for `qanswer`
-- ----------------------------
DROP TABLE IF EXISTS `qanswer`;
CREATE TABLE `qanswer` (
  `qAnswerId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `questId` int(10) NOT NULL,
  `authorId` int(10) NOT NULL,
  `mark` text,
  `note` text,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`qAnswerId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qanswer
-- ----------------------------
INSERT INTO `qanswer` VALUES ('1', '1', '2', '这样，这样，然后在这样这样，接着这样，最后是这样，就得到一个这样的结果了', null, '2016-11-15 19:51:09');

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `questId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `organId` int(10) NOT NULL,
  `questTitle` varchar(30) NOT NULL DEFAULT '',
  `mark` text NOT NULL,
  `authorId` int(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `bestAId` int(10) DEFAULT NULL,
  PRIMARY KEY (`questId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '1', '5C语言程序设计', '求一段快速排序的代码。。', '1', '2016-11-15 22:36:40', '1');
INSERT INTO `question` VALUES ('2', '1', 'Java程序设计', '请一份JDK1.8的中文开发文档，谢谢。', '2', '2016-11-15 19:37:54', null);

-- ----------------------------
-- Table structure for `relation`
-- ----------------------------
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `relationId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) NOT NULL,
  `organId` int(10) NOT NULL,
  `openid` varchar(50) DEFAULT NULL,
  `role` int(1) NOT NULL DEFAULT '1',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`relationId`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES ('1', '2', '1', 'oKPMhv0OiUUc_BPSGjMG22N66Y9s', '2', '2016-11-15 19:20:52');
INSERT INTO `relation` VALUES ('2', '3', '2', null, '1', '2016-11-15 19:22:20');
INSERT INTO `relation` VALUES ('3', '4', '3', null, '1', '2016-11-15 19:22:34');
INSERT INTO `relation` VALUES ('5', '1', '1', 'oKPMhvzCVqEEHME9pKQ-Z3oxLwYo', '1', '2016-11-15 19:23:06');
INSERT INTO `relation` VALUES ('6', '1', '3', 'oKPMhvzCVqEEHME9pKQ-Z3oxLwYo', '3', '2016-11-15 19:23:12');
INSERT INTO `relation` VALUES ('7', '1', '4', 'oKPMhvzCVqEEHME9pKQ-Z3oxLwYo', '1', '2016-11-15 19:23:31');
INSERT INTO `relation` VALUES ('49', '1', '23', 'oKPMhvzCVqEEHME9pKQ-Z3oxLwYo', '1', '2016-11-18 03:05:39');
INSERT INTO `relation` VALUES ('50', '1', '24', null, '1', '2016-11-18 21:46:33');
INSERT INTO `relation` VALUES ('51', '1', '25', null, '1', '2016-11-18 21:46:39');
INSERT INTO `relation` VALUES ('52', '1', '26', null, '1', '2016-11-18 21:47:54');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(10) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone` varchar(11) DEFAULT '',
  `studentId` varchar(11) DEFAULT '',
  `school` varchar(20) DEFAULT '',
  `isWeChat` int(1) NOT NULL,
  `openid` varchar(50) DEFAULT '',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `picture` varchar(20) NOT NULL DEFAULT 'default.jpg',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '林寒戈爸爸', '123@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '13427581250', '', null, '1', 'oKPMhvzCVqEEHME9pKQ-Z3oxLwYo', '2016-11-05 20:11:15', '1.jpg');
INSERT INTO `user` VALUES ('2', '蔡泽胜', '123456789@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', null, '', null, '1', 'oKPMhv0OiUUc_BPSGjMG22N66Y9s', '2016-11-06 15:31:08', 'default.jpg');
INSERT INTO `user` VALUES ('3', '方锐', '983214067@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '13427581251', '', null, '0', null, '2016-11-14 21:00:52', '3.jpg');
INSERT INTO `user` VALUES ('4', '张国洪', '666666@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '15626198155', '', null, '0', null, '2016-11-14 23:16:20', 'default.jpg');

-- ----------------------------
-- Procedure structure for `AddOrganCount`
-- ----------------------------
DROP PROCEDURE IF EXISTS `AddOrganCount`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `AddOrganCount`(in userId int, in id int, in role int)
BEGIN
	insert relation(userId, organId, role)
	values(userId, id, role);
	
	update organization set organCount = organCount+1
	where organId = id;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `updateHAnswerStatus`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateHAnswerStatus`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateHAnswerStatus`(IN fadeHomeworkId INT,IN fadeHAnswerId INT ,IN fadeUserId INT,IN fadeGrade int)
BEGIN

	DECLARE insert_count INT DEFAULT 0;

		START TRANSACTION ;

		INSERT IGNORE grade(homeworkId,answerId,userId,grade) VALUES(fadeHomeworkId,fadeHAnswerId,fadeUserId,fadeGrade);  ##先插入评分

		SELECT ROW_COUNT() INTO insert_count;

		IF(insert_count = 0) THEN

			ROLLBACK ;

		ELSEIF(insert_count < 0) THEN

			ROLLBACK ;

		ELSE   ##已经插入，更新答案表

			if(fadeGrade = 5)then

				UPDATE hanswer SET grade5 = grade5 +1 WHERE hAnswerId = fadeHAnswerId;	

				COMMIT;    ##更新成功，事务提交

			elseif(fadeGrade = 4)then

				UPDATE hanswer SET grade4 = grade4 +1 WHERE hAnswerId = fadeHAnswerId;

				commit;

			else

				UPDATE hanswer SET grade3 = grade3 +1 WHERE hAnswerId = fadeHAnswerId;

				COMMIT;

			end if;

		END IF;

    END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `updateHomeworkStatus`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateHomeworkStatus`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateHomeworkStatus`(IN fadeHomeworkId INT,IN fadeAuthorId INT ,IN fadeMark TEXT,IN fadeNote TEXT)
BEGIN

	DECLARE insert_count INT DEFAULT 0;

	DECLARE user_id INT DEFAULT 0;

	START TRANSACTION ;

	INSERT ignore hanswer(homeworkId,authorId,mark,note) VALUES(fadeHomeworkId,fadeAuthorId,fadeMark,fadeNote);  ##先插入答案信息

	SELECT ROW_COUNT() INTO insert_count;

	IF(insert_count = 0) THEN

	ROLLBACK ;

	ELSEIF(insert_count < 0) THEN

		ROLLBACK ;

	ELSE   ##已经插入，更新作业表

		SELECT userId FROM USER WHERE userId = fadeAuthorId INTO user_id;

		UPDATE homework SET submitCount = submitCount +1,`status`= concat_ws('&',`status`,user_id) WHERE homeworkId = fadeHomeworkId;	

		COMMIT;    ##更新成功，事务提交

	END IF;

  END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for `updateOpenid`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateOpenid`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateOpenid`(IN fadeOpenid varchar(50),IN fadeUserId INT)
BEGIN

		DECLARE update_count INT DEFAULT 0;

		START TRANSACTION ;

		update `user` set openid = fadeOpenid,isWeChat = 1 Where userId = fadeUserId;  ##更新用户对象

		SELECT ROW_COUNT() INTO update_count;

		IF(update_count = 0) THEN

			ROLLBACK ;

		ELSEIF(update_count < 0) THEN

			ROLLBACK ;

		ELSE   ##已经更新，更新关系表

			update relation  Set openid = fadeOpenid where userId = fadeUserId;

			COMMIT;    ##更新成功，事务提交

		END IF;

    END
;;
DELIMITER ;
