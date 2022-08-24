package com.fandi.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fandi.common.lang.Result;
import com.fandi.entity.Blog;
import com.fandi.entity.Comment;
import com.fandi.service.BlogService;
import com.fandi.service.CommentService;
import com.fandi.util.ShrioUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @GetMapping("/commentList/{blogId}")
    public Result getCommentList(@PathVariable("blogId") Long blogId) {
        List<Comment> commentList = commentService.list(new QueryWrapper<Comment>().eq("blog_id", blogId));
        return Result.succ(commentList);
    }
    @RequiresAuthentication
    @PostMapping("/commentList/edit")
    public Result CommentEdit(@Validated @RequestBody Comment comment) {
        Comment temp = null;
        temp = new Comment();
        temp.setUserId(ShrioUtil.getProfile().getId());
        temp.setCreated(LocalDateTime.now());

        BeanUtil.copyProperties(comment, temp, "id", "userId", "created");
        commentService.save(temp);

        //更新blog的评论数
        Long blogId = comment.getBlogId();
        Blog blog = blogService.getById(blogId);
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", blogId).set("comment_count", blog.getCommentCount() + 1);
        blogService.update(null, updateWrapper);

        return Result.succ(null);
    }

    @GetMapping("/commentList/delete/{commentId}")
    public Result CommentDelete(@PathVariable("commentId") Long commentId) {
        Comment comment = commentService.getById(commentId);
        Long blogId = comment.getBlogId();
        commentService.removeById(commentId);

        //修改blog的评论数
        Blog blog = blogService.getById(blogId);
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", blogId).set("comment_count", blog.getCommentCount() - 1);
        blogService.update(null, updateWrapper);

        return Result.succ(null);
    }

}
