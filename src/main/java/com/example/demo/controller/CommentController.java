package com.example.demo.controller;

import com.example.demo.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    private CommentService commentService;
}
