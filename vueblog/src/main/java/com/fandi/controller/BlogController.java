package com.fandi.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandi.common.lang.Result;
import com.fandi.entity.Blog;
import com.fandi.entity.Favorite;
import com.fandi.entity.User;
import com.fandi.service.BlogService;
import com.fandi.service.FavoriteService;
import com.fandi.service.UserService;
import com.fandi.util.ShrioUtil;
import com.fandi.vo.BlogVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;
    @Autowired
    FavoriteService favoriteService;
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));


        return Result.succ(pageData);
    }

    @GetMapping("/blogs/{id}")
    public Result detail(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        User user = userService.getById(blog.getUserId());
        Assert.notNull(blog, "该博客已被删除");
        BlogVO blogVO = new BlogVO();
        BeanUtil.copyProperties(blog, blogVO);
        blogVO.setNickname(user.getUsername());
        blogVO.setAvatar(user.getAvatar());

        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("view_count", blog.getViewCount() + 1);
        blogService.update(null, updateWrapper);

        return Result.succ(blogVO);

    }
    //需要登录之后才能访问的接口
    @RequiresAuthentication
    @PostMapping("/blogs/edit")
    public Result edit(@Validated @RequestBody Blog blog){
        Blog temp = null;
        if(blog.getId() != null){
            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().longValue() == ShrioUtil.getProfile().getId().longValue(), "没有权限编辑");

        }else{
            temp = new Blog();
            temp.setUserId(ShrioUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
            temp.setThumbs(0L);
            temp.setCommentCount(0);
            temp.setFavorites(0L);
            temp.setViewCount(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status", "thumbs","favorites", "commentCount", "viewCount");
        blogService.saveOrUpdate(temp);


        return Result.succ(null);

    }
    @RequiresAuthentication
    @GetMapping("/blogs/thumbsUp/{blogId}")
    public Result thumbsUp(@PathVariable(name = "blogId") Long blogId){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog =blogService.getById(blogId);
        updateWrapper.eq("id",blogId);
        Blog newBlog = new Blog();
        newBlog.setThumbs(blog.getThumbs() + 1);
        blogService.update(newBlog, updateWrapper);
        return Result.succ("点赞成功");
    }

    @RequiresAuthentication
    @GetMapping("/blogs/favorite/{blogId}")
    public Result addFavorite(@PathVariable(name = "blogId") Long blogId){
        UpdateWrapper<Blog> updateWrapper = new UpdateWrapper<>();
        Blog blog =blogService.getById(blogId);
        updateWrapper.eq("id", blogId).set("favorites", blog.getFavorites() + 1);
        blogService.update(null, updateWrapper);

        //更新favorite表
        Favorite f = new Favorite();
        f.setBlogId(blog.getId());
        f.setUserId(ShrioUtil.getProfile().getId());
        f.setCreated(LocalDateTime.now());
        favoriteService.save(f);
        return Result.succ("收藏成功");
    }

}
