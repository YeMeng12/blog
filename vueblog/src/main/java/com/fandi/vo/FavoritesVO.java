package com.fandi.vo;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class FavoritesVO {
    private Long blogId;

    private String title;

    private String description;

    private LocalDateTime created;

}
