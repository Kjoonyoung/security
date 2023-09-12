package com.example.security.domain.notice.repository;

import com.example.security.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query("SELECT n FROM Notice n JOIN FETCH n.createdBy")
    List<Notice> findAllCreatedByFetchJoin();
}
