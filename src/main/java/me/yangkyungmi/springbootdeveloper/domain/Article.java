package me.yangkyungmi.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", nullable = false)
  private String content;

  @Builder
  public Article(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
