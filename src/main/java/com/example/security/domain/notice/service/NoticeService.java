package com.example.security.domain.notice.service;

import com.example.security.domain.account.entity.Account;
import com.example.security.domain.notice.dto.NoticeDetailResponse;
import com.example.security.domain.notice.dto.NoticeListResponse;
import com.example.security.domain.notice.entity.Notice;
import com.example.security.domain.notice.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public List<NoticeListResponse> searchList() {
        List<Notice> list = noticeRepository.findAll();

        return list.stream()
                .map(notice -> NoticeListResponse.builder()
                        .id(notice.getId())
                        .writerName(notice.getCreatedBy().getName())
                        .createdDate(notice.getCreatedDate())
                        .subject(notice.getSubject())
                        .content(notice.getContent())
                        .build()
                )
                .toList();
    }

    @Transactional
    public Long createNotice(NoticeDetailResponse request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notice entity = Notice.builder()
                .createdBy(account)
                .createdDate(LocalDateTime.now())
                .subject(request.getSubject())
                .content(request.getContent())
                .build();
        return noticeRepository.save(entity).getId();
    }
}
