package com.example.security.domain.notice;

import com.example.security.domain.notice.dto.CreateNotice;
import com.example.security.domain.notice.dto.NoticeDetailResponse;
import com.example.security.domain.notice.dto.NoticeListResponse;
import com.example.security.domain.notice.dto.UpdateNotice;
import com.example.security.domain.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping
    public ResponseEntity<List<NoticeListResponse>> searchList() {
        return ResponseEntity.ok(noticeService.searchList());
    }

    @PostMapping("/management")
    public ResponseEntity<Long> createNotice(@RequestBody CreateNotice request) {
        return ResponseEntity.ok(noticeService.createNotice(request));
    }

    @GetMapping("/management/content/{id}")
    public ResponseEntity<NoticeDetailResponse> contentNotice(@PathVariable("id") Long id) {
        return ResponseEntity.ok(noticeService.getNoticeDetail(id));
    }

    @PutMapping("/management/update/{id}")
    public ResponseEntity<Long> updateNotice(@PathVariable("id") Long id, @RequestBody UpdateNotice request) {
        return ResponseEntity.ok(noticeService.updateNotice(id, request));
    }

    // 공지사항 삭제
    @DeleteMapping("/management/delete/{id}")
    public ResponseEntity<Boolean> deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return ResponseEntity.ok().build(); // 실행 했을때 에러가 없으면 ok
    }
}
