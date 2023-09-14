package com.example.security.domain.notice;

import com.example.security.domain.notice.dto.NoticeDetailResponse;
import com.example.security.domain.notice.dto.NoticeListResponse;
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
    public ResponseEntity<Long> createNotice(@RequestBody NoticeDetailResponse response) {
        return ResponseEntity.ok(noticeService.createNotice(response));
    }

    @GetMapping("/management/content/{id}")
    public ResponseEntity<NoticeDetailResponse> contentNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.contentNotice(id));
    }

    @PutMapping("/management/update/{id}")
    public ResponseEntity<Long> updateNotice(@PathVariable Long id, @RequestBody NoticeDetailResponse response) {
        return ResponseEntity.ok(noticeService.updateNotice(id, response));
    }

    @DeleteMapping("/management/delete/{id}")
    public ResponseEntity<Long> deleteNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.deleteNotice(id));
    }
}
