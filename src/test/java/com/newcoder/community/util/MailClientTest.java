package com.newcoder.community.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailClientTest {
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void sendMailTest() {
        // 发送简单文本邮件
        //mailClient.sendMail("jijunyun@outlook.com", "Hello Word!", "This is a test.");

        // 调模板引擎，如果是 MVC，那么 Controller 中的方法只需要返回模板的路径即可
        Context context = new Context();
        context.setVariable("username", "Irving");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailClient.sendMail("jijunyun@outlook.com", "Welcome", content);
    }


}
