package me.yangkyungmi.springbootdeveloper.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import me.yangkyungmi.springbootdeveloper.domain.Article;
import me.yangkyungmi.springbootdeveloper.dto.AddArticleRequest;
import me.yangkyungmi.springbootdeveloper.repository.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

  @Mock
  private BlogRepository blogRepository;

  @InjectMocks
  private BlogService blogService;

  @DisplayName("블로그 글을 추가한다.")
  @Test
  void testSaveArticle() {
    // given (초기조건  || 환경 || 제약조건 등)
    LocalDateTime now = LocalDateTime.now();
    String expectedTitle = "오늘의 스터디 : 테스트 구문 작성";
    Article article = new Article(1L, expectedTitle, "내용", now, now);

    AddArticleRequest request = new AddArticleRequest(expectedTitle, "내용");
    given(blogRepository.save(any())).willReturn(article);
    // when (수행)
    Article result = blogService.save(request);

    // then (검증)
    assertThat(result).isNotNull();
    assertThat(result.getTitle()).isEqualTo(request.getTitle());
  }
}