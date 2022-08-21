package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.CommentEntity;

import com.example.demo.entity.User;

import com.example.demo.repository.ArticlesRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import javax.xml.stream.events.Comment;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticlesRepository articlesRepository;


//    @Autowired
//    public CommentService(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }

    @Transactional
    public String postComment(Long id, CommentDto commentDto) {

        Articles articles = articlesRepository.findById(id)
                .orElseThrow(()-> new NullPointerException("해당 게시물이 존재하지 않습니다."));

        CommentEntity comment = new CommentEntity(commentDto);
        articles.addComment(comment);
        articles.setCommentCount(articles.getCommentList().size());

        commentRepository.save(comment);

        return "댓글이 생성되었습니다. ";
   }

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
