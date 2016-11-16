package com.test.service;

import com.test.dao.*;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.exception.CauseTroubleException;
import com.test.exception.timeline.BestAnswerExistException;
import com.test.exception.timeline.QanswerFormatterWrongException;
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
    @Autowired
    private CommentDao commentDao;



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
        }

        //组装时间轴
        List<Set> everyDayLIst =new ArrayList<Set>();
        Set[] sets = new HashSet[3];
            for (int i = 0; i < sets.length; i++) {
                sets[i] = new HashSet();
                    for (Homework homework:homeworks) {
                        if(homework.getCreateTime().after(new Date(time.getTime()-86400000*i))&&
                                homework.getCreateTime().before(new Date(time.getTime()-86400000*(i-1))) ){
                            sets[i].add(homework);
                        }
                    }
                    for (Inform inform:informs) {
                        if (inform.getCreateTime().after(new Date(time.getTime()-86400000*(i)))&&
                                inform.getCreateTime().before(new Date(time.getTime()-86400000*(i-1)))){
                                sets[i].add(inform);
                        }
                    }
                    for (Question question : questions) {
                        if (question.getCreateTime().after(new Date(time.getTime()-86400000*(i)))&&
                                question.getCreateTime().before(new Date(time.getTime()-86400000*(i-1)))) {
                            sets[i].add(question);
                        }
                    }
            }


        Map content = new HashMap();
        Map day01 = new HashMap();
        Map day02 = new HashMap();
        Map day03 = new HashMap();
        day03.put("time",new Date(time.getTime()+86400000));
        day03.put("message",sets[0]);
        day02.put("time",new Date(time.getTime()));
        day02.put("message",sets[1]);
        day01.put("time",new Date(time.getTime()-86400000));
        day01.put("message",sets[2]);
        content.put("day01",day01);
        content.put("day02",day02);
        content.put("day03",day03);
        return new RequestResult<Map>(StatEnum.TIMELINE_GET_SUCCESS, content);
    }

    /**
     * 查找作业及作业答案
     * @param homeworkId 作业id
     * @param userId     用户id
     * @return dto对象
     */
    public RequestResult<Map> showHomework(int homeworkId, int userId) {
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
        //获取前端评论集合
        List<Comment> comments = commentDao.selectCommentByAll(0,homeworkId);
        Map content = new HashMap();
        content.put("homework",homework);
        content.put("myAnswer",myAnswer);
        content.put("allAnswers",allAnswers);
        content.put("comments",comments);//记得提醒前端
        return new RequestResult<Map>(StatEnum.HOMEWORK_GET_SUCCESS,content);
    }

    /**
     * 获取具体公告
     * @param informId 公告id
     * @return dto对象
     */
    public RequestResult<Inform> showInform(int informId) {
        //获取公告对象
        Inform inform = informDao.selectOneById(informId);
        return new RequestResult<Inform>(StatEnum.INFORM_GET_SUCCESS,inform);
    }

    /**
     * 获取具体请求
     * @param questId 请求对象id
     * @return dto对象
     */
    public RequestResult<Map> showQuestion(int questId,int userId) {
        //获取请求对象
        Question question = questionDao.selectOneById(questId);
        //获取我的答案
        QAnswer myAnswer = qAnswerDao.selectOneById(questId,userId);
        //获取全部请求的回答
        List<QAnswer> allAnswers = qAnswerDao.selectAllByQuestId(questId);
        //获取评论集合
        List<Comment> comments = commentDao.selectCommentByAll(1,questId);
        Map content = new HashMap();
        content.put("question",question);
        content.put("myAnswer",myAnswer);
        content.put("allAnswers",allAnswers);
        content.put("comments",comments);//记得提醒前端
        return new RequestResult<Map>(StatEnum.QUESTION_GET_SUCCESS,content);
    }


    /**
     * 作业答案提交方法
     * @param hAnswer 作业答案对象
     * @return dto对象
     */
    public RequestResult<?> submitHAnswer(HAnswer hAnswer) {

        //判断答案是否超时
        if(new Date().after(homeworkDao.selectOneById(hAnswer.getHomeworkId()).getEndingTime())) {
            throw new SubmitTimeOutException("提交答案超时");
        }
        //对答案内容进行判断
        if(
                !hAnswer.getMark().matches("[\\s\\S]*")
                ){
            throw new SubmitFormatterWrongException("提交答案格式错误");
        }
        //判断作业是否提交过，hAnswerId是否为0
        if (hAnswer.gethAnswerId()==0){
            //为0则未提交过，直接插入
            hAnswerDao.insertHAnswer(hAnswer);
        }else {
            //不为0则已提交，更新数据库
            hAnswerDao.updateHAnswer(hAnswer);
        }
        return new RequestResult<Object>(StatEnum.SUBMIT_HANSWER_SUCCESS);

    }

    /**
     * 请求回答提交方法
     * @param qAnswer 请求回答对象
     * @return dto对象
     */
    public RequestResult<?> submitQAnswer(QAnswer qAnswer) {

        //判断是否已有最佳答案
        if (questionDao.selectOneById(qAnswer.getQuestId()).getqAnswer()!=null) {
            throw new BestAnswerExistException("已存在最佳回答");
        }
        //对答案内容进行判断
        if(
                !qAnswer.getMark().matches("[\\s\\S]*")
                ){
            throw new QanswerFormatterWrongException("回答格式错误");
        }
        //判断作业是否提交过，qAnswerId是否为0
        if (qAnswer.getqAnswerId()==0){
            //为0则未提交过，直接插入
            qAnswerDao.insertQAnswer(qAnswer);
        }else {
            //不为0则已提交，更新数据库
            qAnswerDao.updateQAnswer(qAnswer);
        }
        return new RequestResult<Object>(StatEnum.SUBMIT_QANSWER_SUCCESS);
    }

    /**
     * 设置最佳回答
     * @param questId 请求对象id
     * @param bestAId 回答对象id
     * @param userId 用户id
     * @return dto对象
     */
    public RequestResult<?> setBestAnswer(int questId ,int bestAId,int userId) {
        //判断
        Question question = questionDao.selectOneById(questId);
        if(question==null) {
            throw new CauseTroubleException("搞事的");
        }else if(question.getAuthor().getUserId()!=userId){
            throw new CauseTroubleException("搞事的");
        }else {
            questionDao.setBestAId(questId,bestAId);
            return new RequestResult<Object>(StatEnum.BEST_ANSWER_SET_SUCCESS);
        }
    }

    /**
     * 评分
     * @param grade 评分对象
     * @return dto对象
     */
    public RequestResult<?> score(Grade grade) {
        if(grade==null) {
            throw new CauseTroubleException("搞事的");
        }else if(homeworkDao.selectOneById(grade.getHomeworkId())==null||
                userDao.selectOneById(grade.getUserId())==null||
                grade.getGrade()>5){
            throw new CauseTroubleException("搞事的");
        }else {
            gradeDao.insertGrade(grade);
            return new RequestResult<Object>(StatEnum.SCORE_SUCCESS);
        }
    }

    /**
     * 发布作业
     * @param homework
     * @return
     */
    public RequestResult<?> releaseHomework(Homework homework) {

        homeworkDao.insertHomework(homework);
        return new RequestResult<Object>(StatEnum.RELEASE_HOMEWORK_SUCCESS);
    }

    /**
     * 发布公告
     * @param inform
     * @return
     */
    public RequestResult<?> releaseInform(Inform inform) {
        informDao.insertInform(inform);
        return new RequestResult<Object>(StatEnum.RELEASE_INFORM_SUCCESS);
    }

    /**
     * 发布请求
     * @param question
     * @return
     */
    public RequestResult<?> releaseQuestion(Question question) {
        questionDao.insertQuestion(question);
        return new RequestResult<Object>(StatEnum.RELEASE_QUESTION_SUCCESS);
    }
}
