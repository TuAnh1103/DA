package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.report.ReportSaveRequest;
import com.viuniteam.socialviuni.dto.response.report.ReportResponse;
import com.viuniteam.socialviuni.service.ReportService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;
    private final Profile profile;

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

    @PostMapping("/delete/{reportId}")
    public void reportShare(@PathVariable("reportId") Long reportId){
        reportService.removeReport(reportId);
    }

    @PostMapping
    public Page<ReportResponse> getAllByUserSource(@RequestBody PageInfo pageInfo){
        PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
        return reportService.getAllByUserSource(userService.findOneById(profile.getId()),pageRequest);
    }
}
