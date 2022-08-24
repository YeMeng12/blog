package com.fandi.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fandi.common.lang.Result;
import com.fandi.entity.Blog;
import com.fandi.entity.Comment;
import com.fandi.entity.Favorite;
import com.fandi.entity.User;
import com.fandi.mapper.BlogMapper;
import com.fandi.mapper.FavoriteMapper;
import com.fandi.service.BlogService;
import com.fandi.service.FavoriteService;
import com.fandi.service.UserService;
import com.fandi.vo.FavoritesVO;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    FavoriteService favoriteService;

    @Autowired
    BlogMapper blogMapper;


    @RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/save")
    public  Result save(@Validated @RequestBody User user){

        return Result.succ(user);
    }
    @GetMapping("/getFavorite/{userId}")
    public Result getFavorite(@RequestParam(defaultValue = "1") Integer currentPage, @PathVariable(name = "userId")  Long userId) {
        Page page = new Page(currentPage, 10);
        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("blog_id", "created").eq("user_id",userId);
        List<Favorite> favoriteList = favoriteService.list(queryWrapper);
        List<Long> blogIds= new ArrayList<Long>();
        for(Favorite f : favoriteList){
            blogIds.add(f.getBlogId());
        }
        List<Blog> favoriteList1 = blogMapper.selectBatchIds(blogIds);
        List<FavoritesVO> records = new ArrayList<>();
        for(int i = 0; i < favoriteList1.size(); i++){
            FavoritesVO favoritesVO = new FavoritesVO();
            favoritesVO.setBlogId(favoriteList1.get(i).getId());
            favoritesVO.setTitle(favoriteList1.get(i).getTitle());
            favoritesVO.setDescription(favoriteList1.get(i).getDescription());
            favoritesVO.setCreated(favoriteList.get(i).getCreated());
            records.add(favoritesVO);
        }


        return Result.succ(MapUtil.builder()
                .put("total", favoriteList.size())
                .put("records", records)
                .map());
    }
}
