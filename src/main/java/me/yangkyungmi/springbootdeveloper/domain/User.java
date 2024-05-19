package me.yangkyungmi.springbootdeveloper.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "nickname", unique = true)
  private String nickname;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password")
  private String password;

  @Builder
  public User(String email, String password, String nickname) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
  }

  // 사용자 이름 변경
  public User update(String nickname) {
    this.nickname = nickname;

    return this;
  }

  // UserDetails 클래스를 상속하여 필수 오버라이드 메소드 정의 필요.
  @Override // 권한 반환
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("user"));
  }

  @Override // 사용자의 id 반환
  public String getUsername() {
    return email;
  }

  @Override // 사용자의 password 반환
  public String getPassword() {
    return password;
  }

  @Override // 계정 만료 여부 반환
  public boolean isAccountNonExpired() {
    return true; // true : 만료되지 않았음
  }

  @Override // 계정 잠금 여부 반환
  public boolean isAccountNonLocked() {
    return true; // true : 잠금되지 않았음
  }

  @Override // 패스워드 만료 여부 반환
  public boolean isCredentialsNonExpired() {
    return true; // true : 만료되지 않았음
  }

  @Override // 계정 사용 가능 여부 반환
  public boolean isEnabled() {
    return true; // true : 사용 가능
  }
}
