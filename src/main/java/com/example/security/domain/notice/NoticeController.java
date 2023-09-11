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
    public ResponseEntity<Long> createNotice(@RequestBody NoticeDetailResponse request) {
        return ResponseEntity.ok(noticeService.createNotice(request));
    }
}
