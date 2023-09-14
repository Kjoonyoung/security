package com.example.security.domain.notice.entity;

import com.example.security.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    // jpa n + 1 문제 공부
    // jpql
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 맵핑 lazy는 필요할때만 호출
    @JoinColumn(name = "created_by")
    private Account createdBy;

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 맵핑 lazy는 필요할때만 호출
    @JoinColumn(name = "modified_by")
    private Account modifiedBy;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String subject;

    private String content;

}
