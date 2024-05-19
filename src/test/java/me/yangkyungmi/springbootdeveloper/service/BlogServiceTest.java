package me.yangkyungmi.springbootdeveloper.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import me.yangkyungmi.springbootdeveloper.domain.Article;
import me.yangkyungmi.springbootdeveloper.dto.AddArticleRequest;
import me.yangkyungmi.springbootdeveloper.dto.UpdateArticleRequest;
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
    Article article = Article.builder()
        .author("user1")
        .title("title")
        .content("content")
        .build();

    AddArticleRequest request = new AddArticleRequest(expectedTitle, "내용");
    given(blogRepository.save(any())).willReturn(article);
    // when (수행)
    Article result = blogService.save(request, "user1");

    // then (검증)
    assertThat(result).isNotNull();
    assertThat(result.getTitle()).isEqualTo(request.getTitle());
  }

  @DisplayName("블로그 전체 글을 조회한다.")
  @Test
  void testFindAllArticle() {
    // given
    LocalDateTime now = LocalDateTime.now();
    String mockTitle1 = "테스트 코드 작성";
    String mockContent1 = "서비스 테스트 코드를 작성해보아요~~";
    Article mockArticle1 = Article.builder()
        .author("user1")
        .title(mockTitle1)
        .content(mockContent1)
        .build();

    String mockTitle2 = "스프링 부트3 백엔드 개발자 되기";
    String mockContent2 = "책 추천합니다.";
    // Article mockArticle2 = new Article(2L, mockTitle2, mockContent2, now, now);

    // given(blogRepository.findAll()).willReturn(Arrays.asList(mockArticle1, mockArticle2));

    // when
    List<Article> articles = blogService.findAll();

    // then
    assertThat(articles).isNotNull(); // 블로그 글 목록이 null이 아닌지 확인
    assertThat(articles.size()).isEqualTo(2); // 블로그 글 목록이 비어있지 않은지 확인
    // assertThat(articles).contains(mockArticle1, mockArticle2); // 블로그 글 목록에 테스트용 글이 포함되어 있는지 확인
  }

  @DisplayName("특정 블로그 글을 조회한다.")
  @Test
  void testFindArticleById() {
    // given
    long id = 1L;
    LocalDateTime now = LocalDateTime.now();
    String mockTitle1 = "테스트 코드 작성";
    String mockContent1 = "서비스 테스트 코드를 작성해보아요~~";
    // Article mockArticle1 = new Article(id, mockTitle1, mockContent1, now, now);

    // given(blogRepository.findById(id)).willReturn(Optional.of(mockArticle1));

    // when
    Article result = blogService.findById(id);

    // then
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(id);
    assertThat(result.getTitle()).isEqualTo(mockTitle1);
    assertThat(result.getContent()).isEqualTo(mockContent1);
  }

  @DisplayName("블로그 글을 삭제한다.")
  @Test
  void testDeleteArticle() {
    // given
    long id = 1L;
    LocalDateTime now = LocalDateTime.now();
    String mockTitle1 = "테스트 코드 작성";
    String mockContent1 = "서비스 테스트 코드를 작성해보아요~~";
    // Article mockArticle1 = new Article(id, mockTitle1, mockContent1, now, now);

    // blogRepository.save(mockArticle1);

    // when
    blogService.delete(id);

    // then
    assertThat(blogRepository.findById(id)).isEmpty();
  }

  @DisplayName("블로그 글을 수정한다.")
  @Test
  void testUpdateArticle() {
    // given
    long id = 1L;
    LocalDateTime now = LocalDateTime.now();
    String mockTitle1 = "테스트 코드 작성";
    String mockContent1 = "서비스 테스트 코드를 작성해보아요~~";
    // Article mockArticle1 = new Article(id, mockTitle1, mockContent1, now, now);

    // given(blogRepository.findById(any())).willReturn(Optional.of(mockArticle1));

    String newTitle = "수정 제목";
    String newContent = "수정 본문";
    UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

    // when
    Article updateArticle = blogService.update(id, request);

    // then
    assertThat(updateArticle).isNotNull();
    assertThat(updateArticle.getId()).isEqualTo(id);
    assertThat(updateArticle.getTitle()).isEqualTo(newTitle);
    assertThat(updateArticle.getContent()).isEqualTo(newContent);
  }
}