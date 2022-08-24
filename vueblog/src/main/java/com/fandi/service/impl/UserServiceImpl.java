package com.fandi.service.impl;

import com.fandi.entity.User;
import com.fandi.mapper.UserMapper;
import com.fandi.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
