package com.fandi.service.impl;

import com.fandi.entity.Comment;
import com.fandi.mapper.CommentMapper;
import com.fandi.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
