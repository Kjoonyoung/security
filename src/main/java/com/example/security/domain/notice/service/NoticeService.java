package com.example.security.domain.notice.service;

import com.example.security.domain.account.entity.Account;
import com.example.security.domain.notice.dto.NoticeDetailResponse;
import com.example.security.domain.notice.dto.NoticeListResponse;
import com.example.security.domain.notice.entity.Notice;
import com.example.security.domain.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Long createNotice(NoticeDetailResponse response) {
        //account 정보 확인 : Account.Id
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Notice entity = Notice.builder()
                .createdBy(account)
                .createdDate(LocalDateTime.now())
                .subject(response.getSubject())
                .content(response.getContent())
                .build();
        return noticeRepository.save(entity).getId();
    }

    @Transactional
    public NoticeDetailResponse contentNotice(Long id) {
        Optional<Notice> contentNotice = noticeRepository.findById(id);
        NoticeDetailResponse response = new NoticeDetailResponse();

        try {
            if(contentNotice.isPresent()) {
                Notice notice = contentNotice.get();

                response.setId(notice.getId());
                response.setWriterName(notice.getCreatedBy().getName());

                Account accountName = notice.getModifiedBy();
                if (accountName != null) {
                    response.setUpdaterName(accountName.getName());
                } else {
                    response.setUpdaterName(null);
                }

                response.setCreatedDate(notice.getCreatedDate());
                response.setModifiedDate(notice.getModifiedDate());
                response.setSubject(notice.getSubject());
                response.setContent(notice.getContent());

                return response;
            }else {
                return null;
            }
        }catch(NullPointerException ne){
            System.err.println("ne: "+ ne);
        }
        return null;
    }

    @Transactional
    public Long updateNotice(Long id, NoticeDetailResponse response) {
        // 사용자의 정보 불러옴
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 사용자의 권한 확인, 관리자만 수정 가능
        String updateByAdmin = account.getAuthorities().toString();
        Optional<Notice> contentNotice = noticeRepository.findById(id);

        if (updateByAdmin.equals("[ROLE_ADMIN]")) {
            if (contentNotice.isPresent()) {
                Notice notice = contentNotice.get();
                // 업데이트할 내용을 가져와서 설정
                notice.setModifiedBy(account);
                notice.setModifiedDate(LocalDateTime.now());
                notice.setSubject(response.getSubject());
                notice.setContent(response.getContent());
                return null;
            } else {
                System.err.println("게시물을 찾을 수 없습니다.");
                return null;
            }
        } else {
            System.err.println("관리자 권한이 필요합니다.");
            return null;
        }
    }

    @Transactional
    public Long deleteNotice(Long id) {
        Account accountId = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String deleteByAdmin = accountId.getAuthorities().toString();
        // 삭제 권한 확인, 관리자만 삭제 가능
        if (deleteByAdmin.equals("[ROLE_ADMIN]")) {
            noticeRepository.deleteById(id);
            return null;
        } else {
            return null;
        }
    }
}
