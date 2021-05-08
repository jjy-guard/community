package com.newcoder.community.dao;

import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    /**
     * 批量查询帖子
     * @param userId    用户 ID
     * @param offset    起始行
     * @param limit     显示最大的条目数
     * @return
     */
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);


    /**
     * 查询帖子数量
     * @param userId
     * @return
     */
    // Param 注解用以给参数取别名
    // 如果只有一个参数，且在 <if> 里使用，则必须取别名.
    int selectDiscussPostRows(@Param("userId") int userId);
}
