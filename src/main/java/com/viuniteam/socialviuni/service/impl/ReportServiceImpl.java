package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.report.ReportSaveRequest;
import com.viuniteam.socialviuni.entity.*;
import com.viuniteam.socialviuni.enumtype.ReportStatusType;
import com.viuniteam.socialviuni.enumtype.ReportType;
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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserService userService;
    private final Profile profile;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ShareRepository shareRepository;

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


}
