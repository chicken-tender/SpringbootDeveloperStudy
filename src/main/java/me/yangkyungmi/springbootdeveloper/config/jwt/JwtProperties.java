package me.yangkyungmi.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // application.yml에서 작성한 프로퍼티값을 가져와서 사용
public class JwtProperties {

  private String issuer;
  private String secretKey;
}