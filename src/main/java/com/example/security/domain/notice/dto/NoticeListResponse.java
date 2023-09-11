package com.example.security.domain.notice.dto;

import lombok.*;

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
