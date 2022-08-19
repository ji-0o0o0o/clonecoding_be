package com.example.demo.repository;

import com.example.demo.entity.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    List<Articles> findAllByOrderByCreatedAtDesc();
}