package com.example.security.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailResponse {
    private Long id;

    private String writerName;

    private String updaterName;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String subject;

    private String content;
}
