package com.example.demo.entity;

import com.example.demo.util.Time;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CommentEntity extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;





}
