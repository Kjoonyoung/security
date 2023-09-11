package com.example.security.domain.notice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class NoticeDetailResponse {
    private Long id;

    private  String writerName;

    private String updaterName;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String subject;

    private String content;
}
