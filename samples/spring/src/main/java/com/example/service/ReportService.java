package com.example.service;

import com.example.domain.Report;
import com.example.domain.User;
import com.example.form.ReportForm;
import com.example.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Iterable<Report> findByCreatorId(Long creatorId) {
        return reportRepository.findByCreatorId(creatorId);
    }

    public void create(User user, ReportForm reportForm) {
        Report report = new Report();
        report.setTitle(reportForm.getTitle());
        report.setCreator(user);
        reportRepository.saveAndFlush(report);
    }
}
