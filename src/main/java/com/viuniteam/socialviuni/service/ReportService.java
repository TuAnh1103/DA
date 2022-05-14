package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.report.ReportSaveRequest;

public interface ReportService {
    void reportPost(ReportSaveRequest reportSaveRequest);
    void reportComment(ReportSaveRequest reportSaveRequest);
    void reportShare(ReportSaveRequest reportSaveRequest);
    void removeReport(Long reportId);
}
