package com.newcoder.community.service;

import com.newcoder.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DiscussPostServiceTest {
    @Autowired
    private DiscussPostService discussPostService;
    @Test
    public void test() {
        List<DiscussPost> list = discussPostService.findDiscussPosts(0,0,100);
        for (DiscussPost discussPost : list) {
            System.out.println(discussPost);
        }
    }
}
