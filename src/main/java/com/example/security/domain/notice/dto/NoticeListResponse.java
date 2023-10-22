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
public class NoticeListResponse {

    private Long id;

    private String writerName;

    private LocalDateTime createdDate;

    private String subject;

    private String content;
}
