package me.yangkyungmi.springbootdeveloper.repository;

import me.yangkyungmi.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
