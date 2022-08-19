package com.example.demo.controller;


import com.example.demo.dto.ArticlesRequestDto;
import com.example.demo.service.ArticlesService;
import com.example.demo.service.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class ArticlesController {

    private final ArticlesService articlesService;
    private final S3Uploader s3Uploader;

    //이미지 등록
    @PostMapping("/image/{articlesId}")
    public String upload(@PathVariable Long articlesId, MultipartFile multipartFile, String dirName) throws IOException {
        return s3Uploader.upload(articlesId, multipartFile, "img");
    }
    //메인 페이지 작성글 목록 조회
    @GetMapping("")
    public List<ArticlesRequestDto> readAllPost() {
        return articlesService.readAllArticles();
    }

    //메인 페이지 게시글 작성

    //메인 페이지 수정
    @PatchMapping("/{articlesId}")
    public String updateArticles(@PathVariable Long articlesId, @RequestBody ArticlesRequestDto articlesRequestDto) {
        return articlesService.updateArticles(articlesId, articlesRequestDto);
    }

    //메인 페이지 삭제

    @DeleteMapping("/{articlesId}")
    public String deleteArticles(@PathVariable Long articlesId) {
        return articlesService.deleteArticles(articlesId);
    }


}
