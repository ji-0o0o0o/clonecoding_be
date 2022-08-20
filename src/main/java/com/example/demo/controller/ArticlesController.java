package com.example.demo.controller;


import com.example.demo.dto.ArticlesDto;
import com.example.demo.dto.ArticlesRequestDto;
import com.example.demo.entity.Articles;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticlesController {

    private final ArticlesService articlesService;

    //이미지 등록
    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Articles upload(@RequestPart ArticlesDto articlesDto,
                           @RequestPart MultipartFile multipartFile) throws IOException {
        return articlesService.postArticles(multipartFile, articlesDto);
    }
    //메인 페이지 작성글 목록 조회
    @GetMapping("")
    public List<ArticlesRequestDto> readAllPost() {
        return articlesService.readAllArticles();
    }

    //메인 페이지 게시글 작성

    //메인 페이지 수정
    @PatchMapping("/{articlesId}")
    public ArticlesRequestDto updateArticles(@PathVariable Long articlesId, @RequestBody ArticlesDto articlesDto) {
        return articlesService.updateArticles(articlesId, articlesDto);
    }

    //메인 페이지 삭제

    @DeleteMapping("/{articlesId}")
    public String deleteArticles(@PathVariable Long articlesId) {
        return articlesService.deleteArticles(articlesId);
    }


}
