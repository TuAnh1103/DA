package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Report findOneById(Long id);
    Boolean existsByPostAndUserSource(Post post, User user);
    Boolean existsByCommentAndUserSource(Comment comment,User user);
    Boolean existsByShareAndUserSource(Share share,User user);
}
