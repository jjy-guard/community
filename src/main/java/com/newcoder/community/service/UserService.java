package com.newcoder.community.service;

import com.newcoder.community.constant.ActivationState;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommonUtil;
import com.newcoder.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${community.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public User findUserById(int id) {
        return userMapper.selectById(id);
    }

    public Map<String, Object> register(User user) {
        Map<String, Object> res = new HashMap<>();
        // 空值处理
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        } else if (StringUtils.isBlank(user.getUsername())) {
            res.put("usernameMsg", "账号不能为空！");
        } else if (StringUtils.isBlank(user.getPassword())) {
            res.put("passwordMsg", "密码不能为空！");
        } else if (StringUtils.isBlank(user.getEmail())) {
            res.put("emailMsg", "邮箱不能为空！");
        } else if (userMapper.selectByName(user.getUsername()) != null) {
            res.put("usernameMsg", "该用户名已被注册！");
        } else if (userMapper.selectByEmail(user.getEmail()) != null) {
            res.put("emailMsg", "该邮箱已被注册！");
        } else {
            // 注册用户
            user.setSalt(CommonUtil.generateUUID().substring(0, 5));
            user.setPassword(CommonUtil.md5(user.getPassword() + user.getSalt()));
            user.setType(0);    // 初始为普通用户
            user.setStatus(0);   // 初始为未激活状态
            user.setActivationCode(CommonUtil.generateUUID());
            user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
            user.setCreateTime(new Date());
            userMapper.insertUser(user);

            // 发送激活邮件
            // http://localhost:8080/community/activation/101/code
            String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
            Context context = new Context();
            context.setVariable("email", user.getEmail());
            context.setVariable("url", url);
            String content = templateEngine.process("/mail/activation", context);
            mailClient.sendMail(user.getEmail(), "账号激活", content);
        }
        return res;
    }

    public int activate(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getStatus() == 1) {
            return ActivationState.ACTIVATION_REPEAT;
        } else if (code.equals(user.getActivationCode())) {
            userMapper.updateStatus(userId, 1);
            return ActivationState.ACTIVATION_SUCCESS;
        } else {
            return ActivationState.ACTIVATION_FAILURE;
        }
    }
}
