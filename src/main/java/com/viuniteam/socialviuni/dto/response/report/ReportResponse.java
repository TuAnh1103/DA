package com.viuniteam.socialviuni.dto.response.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.viuniteam.socialviuni.dto.BaseDTO;
import com.viuniteam.socialviuni.dto.response.comment.CommentResponse;
import com.viuniteam.socialviuni.dto.response.post.PostResponse;
import com.viuniteam.socialviuni.dto.response.share.ShareResponse;
import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import com.viuniteam.socialviuni.enumtype.ReportStatusType;
import com.viuniteam.socialviuni.enumtype.ReportType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class ReportResponse{

    private Long id;

    @JsonProperty("created_date")
    private Date createdDate;

    @JsonProperty("user_source")
    private UserAuthorResponse userSource;

    @JsonProperty("report_type")
    private ReportType reportType;

    @JsonProperty("status")
    private ReportStatusType status;

    @JsonProperty("post")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PostResponse postResponse;

    @JsonProperty("comment")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CommentResponse commentResponse;

    @JsonProperty("share")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ShareResponse shareResponse;
}
