/*
Navicat MySQL Data Transfer

Source Server         : Mysql
Source Server Version : 50536
Source Host           : 127.0.0.1:3306
Source Database       : qgshare

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2016-11-20 01:02:18
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grade
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hanswer
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of homework
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inform
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of organization
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qanswer
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of relation
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '林寒戈', '123@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '13427581250', '', null, '1', 'wo shi ni ba ba', '2016-11-05 20:11:15', '1.jpg');
INSERT INTO `user` VALUES ('2', '蔡泽胜', '123456789@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '15612345672', '', null, '1', 'oKPMhv0OiUUc_BPSGjMG22N66Y9s', '2016-11-06 15:31:08', '2.jpg');
INSERT INTO `user` VALUES ('3', '方锐', '983214067@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '13427581251', '', null, '0', null, '2016-11-14 21:00:52', '3.jpg');
INSERT INTO `user` VALUES ('4', '张国洪', '666666@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '15626198155', '', null, '0', null, '2016-11-14 23:16:20', 'default.jpg');
INSERT INTO `user` VALUES ('8', '林寒戈', '1491453007@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '13427581251', '', null, '1', 'oKPMhvzCVqEEHME9pKQ-Z3oxLwYo', '2016-11-19 14:54:38', 'default.jpg');
INSERT INTO `user` VALUES ('9', '方锐', '987654321@qq.com', '123456', null, '', null, '1', null, '2016-11-19 22:54:49', 'default.jpg');
INSERT INTO `user` VALUES ('15', '你麻痹', '466400960@qq.com', 'b25c11129d72a31311a99ff0d68dd26a', '13411111111', '', null, '0', null, '2016-11-20 00:56:49', 'default.jpg');

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
