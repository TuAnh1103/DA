package com.viuniteam.socialviuni.controller.admin;

import com.viuniteam.socialviuni.dto.response.report.ReportResponse;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.service.ReportService;
import com.viuniteam.socialviuni.service.UserService;
import com.viuniteam.socialviuni.utils.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/report")
public class AdminReportController {
    private final ReportService reportService;
    private final UserService userService;
    @GetMapping("/{userSourceId}")
    public Page<ReportResponse> getAllByUserSource(@PathVariable("userSourceId") Long userSourceId, @RequestBody PageInfo pageInfo){
        User userSource = userService.findOneById(userSourceId);
        if(userSource!=null){
            PageRequest pageRequest = PageRequest.of(pageInfo.getIndex(), pageInfo.getSize());
            return reportService.getAllByUserSource(userSource,pageRequest);
        }
        throw new ObjectNotFoundException("Tài khoản không tồn tại");
    }
}
