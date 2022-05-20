package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.report.ReportSaveRequest;
import com.viuniteam.socialviuni.dto.response.report.ReportResponse;
import com.viuniteam.socialviuni.dto.utils.comment.CommentResponseUtils;
import com.viuniteam.socialviuni.dto.utils.post.PostResponseUtils;
import com.viuniteam.socialviuni.dto.utils.share.ShareResponseUtils;
import com.viuniteam.socialviuni.dto.utils.user.UserAuthorResponseUtils;
import com.viuniteam.socialviuni.entity.*;
import com.viuniteam.socialviuni.enumtype.ReportStatusType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.repository.CommentRepository;
import com.viuniteam.socialviuni.repository.PostRepository;
import com.viuniteam.socialviuni.repository.ReportRepository;
import com.viuniteam.socialviuni.repository.ShareRepository;
import com.viuniteam.socialviuni.service.ReportService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final Profile profile;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ShareRepository shareRepository;

    private final UserAuthorResponseUtils userAuthorResponseUtils;
    private final PostResponseUtils postResponseUtils;
    private final CommentResponseUtils commentResponseUtils;
    private final ShareResponseUtils shareResponseUtils;

    private void checkReportType(ReportSaveRequest reportSaveRequest){
        if(reportSaveRequest.getType()==null)
            throw new BadRequestException("Loại báo cáo không được để trống");
    }

    @Override
    public void reportPost(ReportSaveRequest reportSaveRequest) {
        checkReportType(reportSaveRequest);
        Post post = postRepository.findOneById(reportSaveRequest.getPostId());
        if(post==null)
            throw new ObjectNotFoundException("Bài viết không tồn tại");
        User userSource = userService.findOneById(profile.getId());
        if(userSource.getId().equals(post.getAuthor().getId()))
            throw new BadRequestException("Không thể tự báo cáo chính mình");
        if(reportRepository.existsByPostAndUserSource(post,userSource))
            throw new BadRequestException("Không thể báo cáo lần 2");
        Report report = Report.builder()
                .userSource(userSource)
                .reportType(reportSaveRequest.getType())
                .post(post)
                .content(reportSaveRequest.getContent())
                .status(ReportStatusType.QUEUE)
                .build();
        reportRepository.save(report);
        throw new OKException("Báo cáo thành công");
    }

    @Override
    public void reportComment(ReportSaveRequest reportSaveRequest) {
        checkReportType(reportSaveRequest);
        Comment comment = commentRepository.findOneById(reportSaveRequest.getCommentId());
        if(comment==null)
            throw new ObjectNotFoundException("Comment không tồn tại");
        User userSource = userService.findOneById(profile.getId());
        if(userSource.getId().equals(comment.getUser().getId()))
            throw new BadRequestException("Không thể tự báo cáo chính mình");
        if(reportRepository.existsByCommentAndUserSource(comment,userSource))
            throw new BadRequestException("Không thể báo cáo lần 2");
        Report report = Report.builder()
                .userSource(userSource)
                .reportType(reportSaveRequest.getType())
                .comment(comment)
                .content(reportSaveRequest.getContent())
                .status(ReportStatusType.QUEUE)
                .build();
        reportRepository.save(report);
        throw new OKException("Báo cáo thành công");
    }

    @Override
    public void reportShare(ReportSaveRequest reportSaveRequest) {
        checkReportType(reportSaveRequest);
        Share share = shareRepository.findOneById(reportSaveRequest.getShareId());
        if(share==null)
            throw new ObjectNotFoundException("Bài chia sẻ không tồn tại");
        User userSource = userService.findOneById(profile.getId());
        if(userSource.getId().equals(share.getUser().getId()))
            throw new BadRequestException("Không thể tự báo cáo chính mình");
        if(reportRepository.existsByShareAndUserSource(share,userSource))
            throw new BadRequestException("Không thể báo cáo 2 lần");
        Report report = Report.builder()
                .userSource(userSource)
                .reportType(reportSaveRequest.getType())
                .share(share)
                .content(reportSaveRequest.getContent())
                .status(ReportStatusType.QUEUE)
                .build();
        reportRepository.save(report);
        throw new OKException("Báo cáo thành công");
    }

    @Override
    public void removeReport(Long reportId) {
        Report report = reportRepository.findOneById(reportId);
        if(report==null)
            throw new ObjectNotFoundException("Báo cáo không tồn tại");
        if(userService.findOneById(profile.getId()).getId().equals(report.getUserSource().getId()) || userService.isAdmin(profile)){
            reportRepository.delete(report);
            throw new OKException("Xóa báo cáo thành công");
        }
        throw new BadRequestException("Không có quyền xóa báo cáo");
    }
    @Override
    public Page<ReportResponse> getAllByUserSource(User user, Pageable pageable) {
        Page<Report> reportList = reportRepository.findAllByUserSourceOrderByIdDesc(user,pageable);
        List<ReportResponse> reportResponseList = new ArrayList<>();
        reportList.stream().forEach(
                report -> {
                    ReportResponse reportResponse = ReportResponse.builder()
                            .id(report.getId())
                            .userSource(userAuthorResponseUtils.convert(report.getUserSource()))
                            .status(report.getStatus())
                            .reportType(report.getReportType())
                            .createdDate(report.getCreatedDate())
                            .build();
                    if(report.getPost()!=null)
                        reportResponse.setPostResponse(postResponseUtils.convert(report.getPost()));
                    if(report.getComment()!=null)
                        reportResponse.setCommentResponse(commentResponseUtils.convert(report.getComment()));
                    if(report.getShare()!=null)
                        reportResponse.setShareResponse(shareResponseUtils.convert(report.getShare()));
                    reportResponseList.add(reportResponse);
                }
        );
        return new PageImpl<>(reportResponseList, pageable, reportResponseList.size());
    }
}
