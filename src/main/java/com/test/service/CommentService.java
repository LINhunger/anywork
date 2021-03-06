package com.test.service;

import com.test.dao.CommentDao;
import com.test.model.Comment;
import com.test.model.Recomment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
public interface CommentService {

    /**
     * 添加评论
     * @param comment
     * @return 成功为1，失败为0
     */
    int insertComment(@Param("comment") Comment comment);

    /**
     * 添加回复
     * @param recomment
     * @return 成功为1，失败为0
     */
    int insertRecomment(@Param("recomment") Recomment recomment);

//    /**
//     * 根据类型和目标id,查找评论
//     * @param type 类型（作业或请求）
//     * @param targetId 作业或请求的id
//     * @return 评论的集合
//     */
//    List<Comment> selectCommentByAll(@Param("type")int type, @Param("targetId") int targetId);
//
//    /**
//     * 根据类型和目标id,查找评论
//     * @param type 类型（作业或请求）
//     * @param commentId 评论id
//     * @return 回复集合
//     */
//    List<Recomment> selectRecommentBy(@Param("type")int type,@Param("commentId") int commentId);

    /**
     * 获得作业或者请求的所有评论回复
     * @param type 类型(作业或请求)
     * @param targetId 作业或请求的id
     * @return 评论集合
     */
    List<Comment> getAllComment(@Param("type") int type, @Param("targetId") int targetId);
}
