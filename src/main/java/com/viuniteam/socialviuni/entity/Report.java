package com.viuniteam.socialviuni.entity;

import com.viuniteam.socialviuni.enumtype.ReportStatusType;
import com.viuniteam.socialviuni.enumtype.ReportType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
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


    @OneToOne(cascade = CascadeType.ALL)
    private Comment comment;

    @OneToOne(cascade = CascadeType.ALL)
    private Post post;
}
