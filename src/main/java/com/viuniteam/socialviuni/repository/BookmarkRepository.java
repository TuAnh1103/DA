package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Bookmark;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Bookmark findOneById(Long id);
    void deleteById(Long id);
    Page<Bookmark> findAllByUserOrderByIdDesc(User user, Pageable pageable);
}
