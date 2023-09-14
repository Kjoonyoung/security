package com.example.security.domain.notice.entity;

import com.example.security.domain.account.entity.Account;
import com.example.security.domain.notice.dto.UpdateNotice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
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
    @JoinColumn(name = "created_by", updatable = false) // 등록만하고 수정은 x
    private Account createdBy;

    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 맵핑 lazy는 필요할때만 호출
    @JoinColumn(name = "modified_by")
    private Account modifiedBy;

    @Column(updatable = false)
    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private String subject;

    private String content;

    // 컨트롤러에서 여기  접근해서 변경
    public void update(Account account, UpdateNotice request) {
        this.modifiedBy = account;
        this.modifiedDate = LocalDateTime.now();
        this.subject = request.getSubject();
        this.content = request.getContent();
    }
}