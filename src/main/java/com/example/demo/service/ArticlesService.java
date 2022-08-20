package com.example.demo.service;

import com.example.demo.dto.ArticlesDto;
import com.example.demo.dto.ArticlesRequestDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.service.s3.S3Uploader;
import com.example.demo.util.Time;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;
    private final Time time;


    public ArticlesService(ArticlesRepository articlesRepository, UserService userService, S3Uploader s3Uploader,
                           Time time) {
        this.articlesRepository = articlesRepository;
        this.userService = userService;
        this.s3Uploader = s3Uploader;
        this.time = time;
    }

    private Long getTime() {
        Articles articles = new Articles();
        return ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());

    }

    // 메안페이지 생성
    public ArticlesRequestDto postArticles(MultipartFile multipartFile, ArticlesDto articlesDto) throws IOException {
//          이미지 업로드
        String image = s3Uploader.upload(multipartFile);
        String username = userService.getSigningUserId();

        Articles articles = new Articles(articlesDto, image, username);
        articlesRepository.save(articles);
//            작성시간 조회

        long rightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
        ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, time.times(rightNow));
        return articlesRequestDto;

    }

    public List<ArticlesRequestDto> readAllArticles() {
        List<Articles> articlesList = articlesRepository.findAllByOrderByCreatedAtDesc();
        List<ArticlesRequestDto> articlesRequestDtoList = new ArrayList<>();


        for (Articles articles:articlesList) {
//            작성시간 조회
            long rightNow = ChronoUnit.MINUTES.between(articles.getCreatedAt(), LocalDateTime.now());
            articlesRequestDtoList.add(new ArticlesRequestDto(articles, time.times(rightNow)));
        }
        return articlesRequestDtoList;
    }

    //메인 페이지 수정

    @Transactional
    public ArticlesRequestDto updateArticles(Long articlesId, ArticlesDto articlesDto) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
        String user = userService.getSigningUserId();

        ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles, null);
        if(user.equals(articles.getUserName())){
            articles.updateArticles(articlesDto);
//            return ResponseEntity.status(HttpStatus.OK).body(articlesDto).toString()+"";//저장한 정보 출력해준다
            return articlesRequestDto;
        }return null;
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

