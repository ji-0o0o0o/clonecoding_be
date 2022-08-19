package com.example.demo.dto;

import com.example.demo.entity.Articles;
import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
public class ArticlesRequestDto {


    private String image;
    private String userName;
    private int likeCount;
    private String content;
    private LocalDateTime createAt;
    private int CommentCount;

    public ArticlesRequestDto(Articles articles) {
        this.image = articles.getImage();
        this.userName = articles.getUsername();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = articles.getCreatedAt();
        this.CommentCount = articles.getCommentCount();
    }
}
