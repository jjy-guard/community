package com.newcoder.community.controller;

import com.newcoder.community.constant.ActivationState;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController implements ActivationState {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model, User user) {
        Map<String, Object> map = userService.register(user);
        if (map == null || map.isEmpty()) {
            model.addAttribute("msg", "注册成功");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }

    @RequestMapping(path = "/activation/{userId}/{code}", method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int res = userService.activate(userId, code);
        Map<String, String> map = new HashMap<>();
        if (res == ACTIVATION_SUCCESS) {
            map.put("msg", "激活成功！");
            map.put("target", "/login");
        } else if (res == ACTIVATION_REPEAT) {
            map.put("mag", "该账户可正常使用，无需重复激活！");
            map.put("target", "/index");
        } else if (res == ACTIVATION_FAILURE) {
            map.put("msg", "激活码不合法，激活失败！");
            map.put("target", "/index");
        }
        model.addAllAttributes(map);
        return "/site/operate-result";
    }
}
