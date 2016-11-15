/*
Navicat MySQL Data Transfer

Source Server         : Mysql
Source Server Version : 50536
Source Host           : 127.0.0.1:3306
Source Database       : qgshare

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2016-11-14 23:02:53
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES ('2', '1', '1', '2', '5');
INSERT INTO `grade` VALUES ('3', '1', '1', '2', '5');
INSERT INTO `grade` VALUES ('4', '1', '1', '1', '5');
INSERT INTO `grade` VALUES ('5', '1', '1', '1', '5');
INSERT INTO `grade` VALUES ('6', '1', '1', '1', '4');
INSERT INTO `grade` VALUES ('7', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('8', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('9', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('10', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('11', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('12', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('13', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('14', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('15', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('16', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('17', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('18', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('19', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('20', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('21', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('22', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('23', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('24', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('25', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('26', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('27', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('28', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('29', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('30', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('31', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('32', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('33', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('34', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('35', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('36', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('37', '1', '29', '1', '4');
INSERT INTO `grade` VALUES ('38', '1', '29', '1', '3');
INSERT INTO `grade` VALUES ('39', '100', '200', '2', '5');
INSERT INTO `grade` VALUES ('40', '100', '200', '2', '5');
INSERT INTO `grade` VALUES ('41', '1', '1', '2', '5');

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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hanswer
-- ----------------------------
INSERT INTO `hanswer` VALUES ('1', '1', '2', '12345ty', '5', '1', '0', '123456', '2016-11-08 19:51:23');
INSERT INTO `hanswer` VALUES ('31', '1', '2', '小丫小二郎', '0', '0', '0', null, '2016-11-08 20:16:58');
INSERT INTO `hanswer` VALUES ('32', '1', '2', '小丫小二郎', '0', '0', '0', null, '2016-11-08 20:17:42');
INSERT INTO `hanswer` VALUES ('33', '1', '2', '小丫小二郎', '0', '0', '0', null, '2016-11-08 20:18:48');
INSERT INTO `hanswer` VALUES ('34', '1', '2', '小丫小二郎', '0', '0', '0', null, '2016-11-08 20:19:40');
INSERT INTO `hanswer` VALUES ('35', '1', '2', '小丫小二郎', '0', '0', '0', null, '2016-11-08 20:21:28');
INSERT INTO `hanswer` VALUES ('36', '1', '2', '小丫小二郎', '0', '0', '0', null, '2016-11-08 20:33:19');
INSERT INTO `hanswer` VALUES ('37', '1', '1', 'abc', '0', '0', '0', 'cba', '2016-11-08 21:19:20');
INSERT INTO `hanswer` VALUES ('39', '1', '2', 'asdasd', '0', '0', '0', '3333', '2016-11-10 17:53:22');

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
  `status` text,
  PRIMARY KEY (`homeworkId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------
INSERT INTO `homework` VALUES ('1', '1', '寒戈', '# 我是你爸爸', '2', '2016-11-08 22:44:53', '2016-11-14 10:26:27', '26', '2&1');
INSERT INTO `homework` VALUES ('3', '1', '泽胜', '# asdasd', '1', '2016-11-11 10:13:56', '2016-11-06 10:13:56', '0', '466400960@qq.com&');
INSERT INTO `homework` VALUES ('4', '1', '方锐', '# 我爱看b站', '1', '2016-11-13 20:12:13', '2016-11-10 20:12:06', '0', null);
INSERT INTO `homework` VALUES ('5', '1', 'hao ji mo', '# la  cha cha', '2', '2016-11-13 21:34:00', '1970-01-01 08:00:00', '0', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inform
-- ----------------------------
INSERT INTO `inform` VALUES ('1', '1', '2016-11-09 11:38:12', '1234567', 'www.2345.com', '1');
INSERT INTO `inform` VALUES ('2', '2', '2016-11-13 11:43:34', '1234567', '66666', '1');

-- ----------------------------
-- Table structure for `organization`
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `organId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `organName` varchar(10) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(100) NOT NULL DEFAULT '',
  `organCount` int(3) NOT NULL,
  PRIMARY KEY (`organId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('1', '同性交友网站', '2016-11-06 10:58:18', '同性交友，解放双手', '1');
INSERT INTO `organization` VALUES ('2', '学习网站', '2016-11-06 11:50:58', '嘿嘿嘿嘿嘿', '2');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qanswer
-- ----------------------------
INSERT INTO `qanswer` VALUES ('1', '1', '1', ' 寒戈 寒戈爸爸 寒戈爸爸最棒了', '123456', '2016-11-06 19:04:44');
INSERT INTO `qanswer` VALUES ('3', '3', '2', '1', '2', '2016-11-06 19:03:53');
INSERT INTO `qanswer` VALUES ('4', '1', '2', '小丫小二郎', 's', '2016-11-08 09:48:56');

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
INSERT INTO `question` VALUES ('1', '1', '开车了，快上车', '## 123', '2', '2016-11-10 16:27:21', '1');
INSERT INTO `question` VALUES ('2', '1', '儿歌', '门前大桥下游过一群鸭', '2', '2016-11-13 10:33:37', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relation
-- ----------------------------
INSERT INTO `relation` VALUES ('2', '1', '1', 'wo shi ni ba ba', '1', '2016-11-06 10:58:53');
INSERT INTO `relation` VALUES ('3', '1', '2', 'wo shi ni ba ba', '1', '2016-11-06 11:51:15');

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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'vi2', '46640096@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', null, '', null, '3', 'wo shi ni ba ba', '2016-11-05 20:11:15', '1.jpg');
INSERT INTO `user` VALUES ('2', '泽胜', '123456789@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', null, '', null, '1', null, '2016-11-06 15:31:08', 'default.jpg');
INSERT INTO `user` VALUES ('28', '寒戈', '983214067@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '15623454567', '', null, '0', null, '2016-11-14 21:00:52', 'default.jpg');

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
		update `user` set openid = fadeOpenid Where userId = fadeUserId;  ##更新用户对象
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
