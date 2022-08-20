package com.example.demo.service;

import com.example.demo.dto.ArticlesDto;
import com.example.demo.dto.ArticlesRequestDto;
import com.example.demo.entity.Articles;
import com.example.demo.entity.User;
import com.example.demo.repository.ArticlesRepository;
import com.example.demo.service.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final UserService userService;
    private final S3Uploader s3Uploader;

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
    public ArticlesRequestDto updateArticles(Long articlesId, ArticlesDto articlesDto) {
        Articles articles = articlesRepository.findById(articlesId)
                .orElseThrow(()->new IllegalArgumentException("해당 게시물이 존재하지않습니다."));
        String user = userService.getSigningUserId();

        ArticlesRequestDto articlesRequestDto = new ArticlesRequestDto(articles);
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


    public Articles postArticles(MultipartFile multipartFile, ArticlesDto articlesDto) throws IOException {
        if (multipartFile != null) {
            String image = s3Uploader.upload(multipartFile);
            String username = userService.getSigningUserId();

            Articles articles = new Articles(articlesDto, image, username);
            articlesRepository.save(articles);
            return articles;
        }
        throw new IllegalArgumentException("이미지를 업로드해주세요");

    }
}

