package com.test.quartz;

import com.test.common.WetChatTest;
import com.test.enums.Level;
import com.test.util.Logger;

/**
 * Created by zggdczfr on 2016/10/25.
 */
public class QuartzJob {

    private static final Logger LOGGER = Logger.getLogger(QuartzJob.class);

    public void workForToken(){
        try {
            WetChatTest wetChatTest = new WetChatTest();
            wetChatTest.getToken_getTicket();
        } catch (Exception e){
            LOGGER.log(Level.ERROR, "自启动获取Token类发生异常 错误提示: {0}", e);
        }
    }
}
