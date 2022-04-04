package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.OffensiveKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OffensiveKeywordRepository extends JpaRepository<OffensiveKeyword,Long> {

    @Query(value = "select * from offensive_keyword where keyword = CONVERT(?1 USING binary)",nativeQuery = true)
    OffensiveKeyword findOneByKeyword(String keyword);
    OffensiveKeyword findOneById(Long id);
}
