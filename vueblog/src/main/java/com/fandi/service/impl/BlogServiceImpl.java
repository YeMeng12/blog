package com.fandi.service.impl;


import com.fandi.entity.Blog;
import com.fandi.mapper.BlogMapper;
import com.fandi.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {


}
