package com.fandi.service.impl;

import com.fandi.entity.Favorite;
import com.fandi.mapper.FavoriteMapper;
import com.fandi.service.FavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {

}
