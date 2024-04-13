package me.yangkyungmi.springbootdeveloper.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yangkyungmi.springbootdeveloper.domain.Article;
import me.yangkyungmi.springbootdeveloper.dto.AddArticleRequest;
import me.yangkyungmi.springbootdeveloper.dto.UpdateArticleRequest;
import me.yangkyungmi.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor //final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BlogService {

  private final BlogRepository blogRepository;

  // 블로그 글 추가 메서드
  public Article save(AddArticleRequest request) {
    return blogRepository.save(request.toEntity());
  }

  // 블로그 전체 글 조회 메서드
  public List<Article> findAll() {
    return blogRepository.findAll();
  }

  // 블로그 특정 글 조회 메서드
  public Article findById(long id) {
    return blogRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
  }

  // 블로그 글 삭제 메서드
  public void delete(long id) {
    blogRepository.deleteById(id);
  }

  // 블로그 글 수정 메서드
  @Transactional
  public Article update(long id, UpdateArticleRequest request) {
    Article article = blogRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

    article.update(request.getTitle(), request.getContent());

    return article;
  }
}
