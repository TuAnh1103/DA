package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface BrowserRepository extends JpaRepository<Browser,Long>{
//    Browser findOneById(Long id);
    void deleteAllByUser(User user);
    Page<Browser> findByUserOrderByIdDesc(User user, Pageable pageable);
}
