package com.example.demo.service;

import com.example.demo.dto.ArticlesRequestDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticlesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final UserService userService;
    // 메안페이지 생성
    public List<ArticlesRequestDto> readAllArticles() {
        List<Articles> articlesList = articlesRepository.findAllByOrderByCreatedAtDesc();
        List<ArticlesRequestDto> articlesRequestDtoList = new ArrayList<>();

        for (Articles articles:articlesList) {
            articlesRequestDtoList.add(new ArticlesRequestDto(articles));
        }
        return articlesRequestDtoList;
    }


    //메인 페이지 수정
    @Transactional
    public String updateArticles(Long articlesId, ArticlesRequestDto articlesRequestDto) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
        String user = userService.getSigningUserId();

        if(user.equals(articles.getUserName())){
            articles.updateArticles(articlesRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body(articlesRequestDto)+"";//저장한 정보 출력해준다
        }return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)+"수정 실패하였습니다.";
    }

    //메인 페이지 삭제
    public String deleteArticles(Long articlesId) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
        String user = userService.getSigningUserId();

        if(user.equals(articles.getUserName())){
            articlesRepository.delete(articles);
            return "삭제가 되었습니다.";
        }else return "삭제가 실패하였습니다.";
    }


}

