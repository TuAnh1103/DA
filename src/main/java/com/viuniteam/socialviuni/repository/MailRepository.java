package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface MailRepository extends JpaRepository<Mail,Long> {
    Mail findOneByEmail(String email);
    void deleteByEmail(String email);
}
