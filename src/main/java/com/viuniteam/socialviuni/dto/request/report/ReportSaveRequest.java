package com.viuniteam.socialviuni.dto.request.report;

import com.viuniteam.socialviuni.enumtype.ReportType;
import lombok.Data;

@Data
public class ReportSaveRequest {
    private Long postId;
    private Long commentId;
    private Long shareId;
    private ReportType type;
    private String content;
}
