package com.fandi.vo;

import com.fandi.entity.Blog;
import lombok.Data;


@Data
public class BlogVO extends Blog {
    //昵称
    private String nickname;
    //头像
    private String avatar;
}
