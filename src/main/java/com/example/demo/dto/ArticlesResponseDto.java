package com.example.demo.dto;

import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticlesResponseDto {


    private String image;
    private String userName;
    private Long likeCount;
    private String content;
    private String createAt;
    private Long CommentCount;
    private List<CommentEntity> commentList;

    public ArticlesResponseDto(Articles articles, String createAt, List<CommentEntity> commentList) {
        this.image = articles.getImage();
        this.userName = articles.getUserName();
        this.likeCount = articles.getLikeCount();
        this.content = articles.getContent();
        this.createAt = createAt;
        this.CommentCount = articles.getCommentCount();
        this.commentList = commentList;
    }
}
