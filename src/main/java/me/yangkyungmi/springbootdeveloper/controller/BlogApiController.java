package me.yangkyungmi.springbootdeveloper.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yangkyungmi.springbootdeveloper.domain.Article;
import me.yangkyungmi.springbootdeveloper.dto.AddArticleRequest;
import me.yangkyungmi.springbootdeveloper.dto.ArticleResponse;
import me.yangkyungmi.springbootdeveloper.dto.UpdateArticleRequest;
import me.yangkyungmi.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

  private final BlogService blogService;

  // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핃
  @PostMapping("/api/articles")
  public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
    Article savedArticle = blogService.save(request);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(savedArticle);
  }

  @GetMapping("/api/articles")
  public ResponseEntity<List<ArticleResponse>> findAllArticles() {
    List<ArticleResponse> articles = blogService.findAll()
        .stream()
        .map(ArticleResponse::new)
        .toList();

    return ResponseEntity.ok()
        .body(articles);
  }

  @GetMapping("/api/articles/{id}")
  // URL 경로에서 값 추출
  public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
    Article article = blogService.findById(id);

    return ResponseEntity.ok()
        .body(new ArticleResponse(article));
  }

  @DeleteMapping("/api/articles/{id}")
  public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
    blogService.delete(id);

    return ResponseEntity.ok()
        .build();
  }

  @PutMapping("/api/articles/{id}")
  public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody
  UpdateArticleRequest request) {
    Article updateArticle = blogService.update(id, request);

    return ResponseEntity.ok()
        .body(updateArticle);
  }
}
