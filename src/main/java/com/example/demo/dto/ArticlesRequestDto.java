package com.example.demo.dto;

import com.example.demo.entity.Articles;
import lombok.Getter;

@Getter
public class ArticlesRequestDto {


    private String image;
    private String userName;
    private int likeCount;
    private String content;
    private String createAt;
    private int CommentCount;

    public ArticlesRequestDto(Articles articles) {
        this.image = articles.getImage();
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = articles.getCreatedAt();
       this.CommentCount = articles.getCommentCount();
    }
}
