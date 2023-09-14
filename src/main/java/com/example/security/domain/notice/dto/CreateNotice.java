package com.example.security.domain.notice.dto;

// 화면에서 가져오는건 setter, getter 사용

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateNotice {
    private String subject;
    private String content;
}
