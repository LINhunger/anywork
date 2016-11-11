DELIMITER $$
CREATE PROCEDURE updateHomeworkStatus(IN fadeHomeworkId INT,IN fadeAuthorId INT ,IN fadeMark TEXT ,IN fadeNote TEXT,OUT fadeResult INT)
  BEGIN
    DECLARE insert_count INT DEFAULT 0;
    START TRANSACTION ;
    INSERT ignore hanswer(homeworkId,authorId,mark,note) VALUES(fadeHomeworkId,fadeAuthorId,fadeMark,fadeNote);  --先插入答案信息
    SELECT ROW_COUNT() INTO insert_count;
    IF(insert_count = 0) THEN
      ROLLBACK ;
      SET fadeResult = -1;   --插入失败
    ELSEIF(insert_count < 0) THEN
      ROLLBACK ;
      SET fadeResult = -2;   --内部错误
    ELSE   --已经插入，更新作业表
      UPDATE homwork SET submitCount = submitCount +1 WHERE homeworkId = fadeHomeworkId;
        COMMIT ;    --更新成功，事务提交
        SET  fadeResult = 1;   --更新成功返回值为1
  END $$

DELIMITER ;

SET @fadeResult = -3;
CALL excuteSeckill(8,13813813822,NOW(),@fadeResult);
SELECT @fadeResult;