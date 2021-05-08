package com.newcoder.community.controller;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.Page;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.DiscussPostService;
import com.newcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private DiscussPostService discussPostService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        // dispatcherServlet 会在方法调用前实例化 Model 和 page，并将 Page 注入进 model 中
        // 因此不需要自己通过 addAttribute 方法将 Page 注入进 Model
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");
        List<Map<String, Object>> userAndDiscussPost = new LinkedList<>();
        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        if (discussPostList != null) {
            for (DiscussPost discussPost : discussPostList) {
                User user = userService.findUserById(discussPost.getUserId());
                Map<String, Object> map = new HashMap<>();
                map.put("user", user);
                map.put("post", discussPost);
                userAndDiscussPost.add(map);
            }
        }
        model.addAttribute("userAndDiscussPost", userAndDiscussPost);
        return "/index";
    }
}
