package com.example.repository;

import com.example.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Iterable<Report> findByCreatorId(Long creatorId);
}
