package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Browser;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BrowserRepository extends JpaRepository<Browser,Long>{
    List<Browser> findAllByUser(User user);
//    Browser findOneById(Long id);
    void deleteAllByUser(User user);
}
