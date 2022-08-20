package com.example.demo.entity;

import com.example.demo.dto.ArticlesDto;
import com.example.demo.dto.ArticlesRequestDto;
import com.example.demo.util.TimeStamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Articles extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articlesId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)// length = ?
    private String content;


    private int likeCount;

    private String image;
    private int CommentCount;



    public Articles(ArticlesDto articlesDto, String image, String userName) {
        this.content = articlesDto.getContent();
        this.image = image;
        this.userName = userName;
    }

    public void updateArticles(ArticlesDto articlesDto) {
        this.content = articlesDto.getContent();
    }

    public void setImage(String url) {
        this.image = url;
    }

//    @OneToMany(cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Comment> commentList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "article") //생성 삭제가 많이 일어나니까 mappedBy
//    @JsonIgnore
//    private List<Like> likeList = new ArrayList<>();
}
