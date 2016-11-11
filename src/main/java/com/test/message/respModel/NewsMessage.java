package com.test.message.respModel;

import java.util.List;

/**
 * 多图文消息
 * Created by zggdczfr on 2016/10/22.
 */
public class NewsMessage extends BaseMessage {

    //图文消息个数，一般限制为10条以内
    private int ArticleCount;
    //多图文消息信息
    private List<Article>  Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
