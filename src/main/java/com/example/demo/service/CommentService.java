package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private ArticlesRepository articlesRepository;
    private UserService userService;
    @Autowired
    public CommentService(CommentRepository commentRepository,
                          ArticlesRepository articlesRepository,
                          UserService userService) {
        this.commentRepository = commentRepository;
        this.articlesRepository = articlesRepository;
        this.userService = userService;
    }

    @Transactional
    public CommentEntity postComment(Long id, CommentDto commentDto) {
        Articles articles = articlesRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시물이 존재하지 않습니다")
        );
        CommentEntity comment = new CommentEntity(articles, commentDto, userService.getSigningUserId());
        return comment;
    }
}
