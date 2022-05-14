package com.viuniteam.socialviuni.entity;

import com.viuniteam.socialviuni.enumtype.ReportStatusType;
import com.viuniteam.socialviuni.enumtype.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_source_id")
    private User userSource;

//    @ManyToOne
//    @JoinColumn(name = "user_target_id")
//    private User userTarget;

    @Column
    private ReportType reportType;

    @Column
    private String content;

    @Column
    private ReportStatusType status;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "share_id")
    private Share share;

}
