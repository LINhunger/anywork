package com.test.service;

import com.test.dao.TextpaperDao;
import com.test.dao.TopicDao;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Textpaper;
import com.test.model.Topic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hunger on 2016/11/25.
 */
@Service
public class TestService {

    @Resource
    private TextpaperDao textpaperDao;
    @Resource
    private TopicDao topicDao;


    /**
     * 发布试卷
     * @param textpaper 试卷对象
     * @return
     */
    @Transactional
    public RequestResult<?> publishTextpaper(Textpaper textpaper) {
            List<Topic>topics = textpaper.getTopics();
            Integer score = 0;
            for (Topic t:topics) {
                score+=t.getScore();
            }
            textpaper.setTextpaperScore(score);
            textpaperDao.insertTextpaper(textpaper);
            Integer textpaperId =  textpaper.getTextpaperId();
        for (Topic t:topics){
            if(textpaper==null||textpaperId == 0) {
                throw  new RuntimeException("插入失败");
            }
            t.setTextpaperId(textpaperId);
            topicDao.insertTopic(t);
        }
        return new RequestResult<Object>(StatEnum.TEXTPAPER_SUBMIT_SUCCESS,textpaperId);
    }


    /**
     * 通过试卷id获取试卷对象
     * @param textpaperId
     * @return
     */
    public RequestResult<Textpaper> getTextpaperById(Integer textpaperId){
        Textpaper textpaper = textpaperDao.selectOne(textpaperId);
        List<Topic> topics = topicDao.selectAll(textpaperId);
        textpaper.setTopics(topics);
        return  new RequestResult<Textpaper>(StatEnum.TEXTPAPER_GET_SUCCESS,textpaper);
    }

    /**
     * 通过试卷标题获取试卷对象
     * @param textpaperTitle
     * @return
     */
    public RequestResult<List> getTextpaperByTitle(String textpaperTitle){
        return  null;
    }

    /**
     *
     * @param textpaper
     * @return
     */
    @Transactional
    public RequestResult<?> updateTextpaper(Textpaper textpaper) {
        textpaperDao.deleteTextpaper(textpaper.getTextpaperId());
        topicDao.deleteTopic(textpaper.getTextpaperId());
        publishTextpaper(textpaper);
        return new RequestResult<Object>(StatEnum.TEXTPAPER_SUBMIT_SUCCESS,textpaper.getTextpaperId());
    }
}
