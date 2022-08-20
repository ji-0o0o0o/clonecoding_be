package com.example.demo.entity;

import com.example.demo.dto.CommentDto;
import com.example.demo.util.TimeStamped;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "ARTICLES_ID")
    @JsonBackReference
    private Articles articles;

    public CommentEntity(CommentDto commentDto) {
        this.comment = commentDto.getComment();
    }

}

