package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.request.report.ReportSaveRequest;
import com.viuniteam.socialviuni.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/post")
    public void reportPost(@RequestBody ReportSaveRequest reportSaveRequest){
        reportService.reportPost(reportSaveRequest);
    }
    @PostMapping("/comment")
    public void reportComment(@RequestBody ReportSaveRequest reportSaveRequest){
        reportService.reportComment(reportSaveRequest);
    }
    @PostMapping("/share")
    public void reportShare(@RequestBody ReportSaveRequest reportSaveRequest){
        reportService.reportShare(reportSaveRequest);
    }

    @DeleteMapping("/{reportId}")
    public void reportShare(@PathVariable("reportId") Long reportId){
        reportService.removeReport(reportId);
    }
}
