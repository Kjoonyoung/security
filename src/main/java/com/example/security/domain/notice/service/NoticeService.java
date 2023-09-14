package com.example.security.domain.notice.service;

import com.example.security.domain.account.entity.Account;
import com.example.security.domain.notice.dto.CreateNotice;
import com.example.security.domain.notice.dto.NoticeDetailResponse;
import com.example.security.domain.notice.dto.NoticeListResponse;
import com.example.security.domain.notice.dto.UpdateNotice;
import com.example.security.domain.notice.entity.Notice;
import com.example.security.domain.notice.repository.NoticeRepository;
import com.example.security.global.config.security.SecurityHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
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
    public Long createNotice(CreateNotice request) {
        // account 정보 확인 : Account.Id
        Account account = SecurityHelper.getAccount();
        Notice entity = Notice.builder()
                // 변경할 컬럼만 사용자가 보낼 수 있어야 함. ex) 제목, 내용
                .createdBy(account)
                .createdDate(LocalDateTime.now())
                .subject(request.getSubject())
                .content(request.getContent())
                .build();
        return noticeRepository.save(entity).getId();
    }

    @Transactional(readOnly = true)
    public NoticeDetailResponse getNoticeDetail(Long id) {
        Notice entity = getNotice(id);

        return NoticeDetailResponse.builder()
                .id(entity.getId())
                .writerName(entity.getCreatedBy().getName())
                .updaterName(entity.getModifiedBy().getName())
                .createdDate(entity.getCreatedDate())
                .modifiedDate(entity.getModifiedDate())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .build();
    }

    @Transactional
    public Long updateNotice(Long id, UpdateNotice request) {
        // 사용자의 정보 불러옴,
        Account account = SecurityHelper.getAccount();
        Notice entity = getNotice(id);
        entity.update(account, request);

        return entity.getId(); // id값을 화면에 다시 리턴
    }

    @Transactional
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    private Notice getNotice(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 데이터입니다."));
    }
}
