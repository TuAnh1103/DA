package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Bookmark;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Bookmark findOneById(Long id);
    void deleteById(Long id);
    List<Bookmark> findAllByUser(User user);
}
