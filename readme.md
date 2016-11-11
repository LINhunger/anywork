1.question类的String questionTile;//请求标题 -> 改为 String questTitle;//请求标题,记得改getter,setter
2.数据库question表的主键需设置无符号和自动递增
3.questionDao类中的34行和41行的返回值类型把QuestionDao修改为Question
4.数据库中的question表的bestAId设置为(可以为null)
5.Homework类,Inform类,Question类里面的name属性值为他们的类名小写
6.QuestionDao类的56行的bestId 改为bestAId.
7.所有Model层前的属性加上private
8.数据库inform表的主键设置无符号和自动递增
9.Dao层类加上注解repository
10.HAnswerDao类新增方法
    /**
     * 根据作业id查找全部作业答案
     * @param homeworkId 作业答案对象
     * @return  作业答案对象集合
     */
    List<HAnswer> selectAllByHomeworkId(@Param("homeworkId")int homeworkId);

11.数据库hanswer表的mark,note字段设置为可以为null
12.QAnswerDao类新增方法
    /**
     * 根据请求id查找全部请求答案
     * @param questId 请求id
     * @return 请求答案集合
     */
    List<QAnswer> selectAllByQuestId(@Param("questId")int questId);
13.数据库qanswer表的mark,note字段设置为可以为null
14.model层的Grade类的homewrkId 改为homeworkId,以及hAnswerId 改为answerId
15.GradeDao层增加方法
    /**
     * 根据答案id与用户id查找评分对象
     * @param answerId 答案id
     * @param userId 用户id
     * @return 评分对象
     */
    Grade selectOneById(@Param("answerId")int answerId,@Param("userId")int userId);
16.数据库user表email字段加索引（名字，栏位，索引类型，索引方式）（email，email,Unique,BTREE）
17.model层HAnswer类增加属性（int status）,HAnswer类中status的属性为1是已经评分，0为未评分
18,dao层中的InformDao、QuestionDao、HomeworkDao中的selectAllByOrganId(@Param("organId"));方法改动（参数增加@Param("time")Date time）
HomeworkDao类
    /**
     * 根据组织id查找作业对象集合
     * @param organId 组织id
     * @param time 时间节点
     * @return 作业对象集合
     */
    List<Homework> selectAllByOrganId(@Param("organId") int organId,@Param("time")Date time);
QuestionDao类
    /**
     * 根据组织id查找全部请求集合
     * @param organId 组织id
     * @param time 时间节点
     * @return question的集合
     */
    List<Question> seclectAllByOrganId(@Param("organId") int organId,@Param("time")Date time);
InformDao类
    /**
     * 查找多条公告
     * @param organId 组织id
     * @param time 时间节点
     * @return 公告对象的集合
     */
    List<Inform> selectAllByOrganId(@Param("organId")int organId,@Param("time")Date time);