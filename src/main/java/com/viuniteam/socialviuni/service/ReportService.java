package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.report.ReportSaveRequest;
import com.viuniteam.socialviuni.dto.response.report.ReportResponse;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    void reportPost(ReportSaveRequest reportSaveRequest);
    void reportComment(ReportSaveRequest reportSaveRequest);
    void reportShare(ReportSaveRequest reportSaveRequest);
    void removeReport(Long reportId);
    Page<ReportResponse> getAllByUserSource(User user, Pageable pageable);
}
