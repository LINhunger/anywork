package com.test.service;

import com.test.dao.*;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.exception.timeline.SubmitFormatterWrongException;
import com.test.exception.timeline.SubmitTimeOutException;
import com.test.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by hunger on 2016/11/9.
 */
@Service
public class TimelineService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private HomeworkDao homeworkDao;
    @Autowired
    private InformDao informDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private HAnswerDao hAnswerDao;
    @Autowired
    private QAnswerDao qAnswerDao;
    @Autowired
    private GradeDao gradeDao;



    /**
     * 获取时间树方法
     *
     * @param organId 组织id
     * @param time    时间
     * @param userId  用户id
     * @return dto对象
     */
    public RequestResult<Map> showTimeline(int organId, Date time, int userId) {

        List<Question> questions = questionDao.seclectAllByOrganId(organId, time);
        List<Inform> informs = informDao.selectAllByOrganId(organId, time);
        List<Homework> homeworks = homeworkDao.selectAllByOrganId(organId, time);
        //添加作业状态 十位数：是否过期，个位数：是否提交
        for (Homework h : homeworks) {
            if (h != null && h.getStatus().contains(userId + "")) {
                h.setStatus("01");//已提交
            } else if (new Date().after(h.getEndingTime())) {
                h.setStatus("10");//过期未提交
            } else {
                h.setStatus("00");//未过期未提交
            }
            return null;
        }
        Map content = new HashMap();
        content.put("questions",questions);
        content.put("informs",informs);
        content.put("homeworks",homeworks);
        return new RequestResult<Map>(StatEnum.TIMELINE_GET_SUCCESS, content);
    }

    /**
     * 查找作业及作业答案
     * @param homeworkId 作业id
     * @param userId     用户id
     * @return dto对象
     */
    public RequestResult<?> showHomework(int homeworkId, int userId) {
        //获取作业对象
        Homework homework = homeworkDao.selectOneById(homeworkId);
        //获取我的答案
        HAnswer myAnswer = null;
        //获取作业的全部答案
        List<HAnswer> allAnswers = hAnswerDao.selectAllByHomeworkId(homeworkId);
        for(HAnswer answer:allAnswers) {
            //如果已经评分，则显分数
            if(gradeDao.selectOneById(answer.gethAnswerId(),userId)!=null){
                answer.setStatus(1);
            }
            //查找自己的答案
            if(userId==answer.getAuthor().getUserId()){
                myAnswer=answer;
            }
        }
        Map content = new HashMap();
        content.put("homework",homework);
        content.put("myAnswer",myAnswer);
        content.put("allAnswers",allAnswers);
        return new RequestResult<Map>(StatEnum.HOMEWORK_GET_SUCCESS,content);
    }

    public RequestResult<?> showInform(int informId) {
        //获取公告对象
        Inform inform = informDao.selectOneById(informId);
        return new RequestResult<Object>(StatEnum.INFORM_GET_SUCCESS,inform);
    }

    //记得有全部答案
    public RequestResult<?> showQuestion(int questId) {
        //获取请求对象
        Question question = questionDao.selectOneById(questId);
        //获取全部请求的回答
        List<QAnswer> allAnswers = qAnswerDao.selectAllByQuestId(questId);
        Map content = new HashMap();
        content.put("question",question);
        content.put("allAnswers",allAnswers);
        return new RequestResult<Object>(StatEnum.QUESTION_GET_SUCCESS,content);
    }

    //
    public RequestResult<?> releaseHomework(Homework homework) {

        return null;
    }

    public RequestResult<?> releaseInform(Inform inform) {
        return null;
    }

    public RequestResult<?> releaseQuestion(Question question) {
        return null;
    }
    //

    //记得拿到hAnwserId,status
    public RequestResult<?> submitHAnswer(HAnswer hAnswer) {

        //判断答案是否超时
        if(new Date().after(homeworkDao.selectOneById(hAnswer.getHomeworkId()).getEndingTime())) {
            throw new SubmitTimeOutException("提交作业超时");
        }
        //对答案内容进行判断
        if(
                !hAnswer.getMark().matches("[\\s\\S]*")
                ){
            throw new SubmitFormatterWrongException("提交作业格式错误");
        }
        //判断作业是否提交过，hAnswerId是否为0
        if (hAnswer.gethAnswerId()!=0){
            //为0则未提交过，直接插入
            hAnswerDao.insertHAnswer(hAnswer);
        }else {
            //不为0则已提交，更新数据库
            hAnswerDao.updateHAnswer(hAnswer);
        }
        return new RequestResult<Object>(StatEnum.SUBMIT_HOMEWORK_SUCCESS);

    }

    //记得拿到qAnwserId
    public RequestResult<?> submitQAnswer(QAnswer qAnswer) {

        //判断是否已有最佳答案
        if (questionDao.selectOneById(qAnswer.getQuestId()).getqAnswer()!=null) {
            throw new
        }
        return null;
    }

    //设置最佳答案
    public RequestResult<?> setBestAnswer(int qAnswerId) {
        return null;
    }

    //评分
    public RequestResult<?> score(Grade grade) {
        return null;
    }

}
