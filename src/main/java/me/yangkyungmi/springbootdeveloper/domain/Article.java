package me.yangkyungmi.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @CreatedDate // 엔티티가 생성될 때 생성 시간을 created_at 컬럼에 저장
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate // 엔티티가 수정될 때 마지막으로 수정된 시간을 updated_at에 저장
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @Column(name = "author", nullable = false)
  private String author;

  @Builder
  public Article(String author, String title, String content) {
    this.author = author;
    this.title = title;
    this.content = content;
  }

  public Article(String expectedTitle, String 내용, LocalDateTime now, LocalDateTime now1) {
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
