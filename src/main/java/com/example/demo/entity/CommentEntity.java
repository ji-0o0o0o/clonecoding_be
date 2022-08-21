package com.example.demo.entity;

import com.example.demo.dto.CommentDto;
import com.example.demo.util.TimeStamped;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@Entity
public class CommentEntity extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "Articles_Id", nullable = false)
    private Articles articles;

    @Column
    private String userName;

    @ManyToOne
    @JoinColumn(name = "ARTICLES_ID")
    @JsonBackReference
    private Articles articles;


    public CommentEntity(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }

    public CommentEntity(Articles articles, CommentDto commentDto, String userName) {
        this.articles = articles;
        this.comment = commentDto.getComment();
        this.userName = userName;
    }

}

