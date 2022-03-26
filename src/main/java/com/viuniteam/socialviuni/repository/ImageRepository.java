package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface ImageRepository extends JpaRepository<Image,Long> {
    Image findOneById(Long id);
    Image save(Image image);

    void deleteById(Long id);

    @Modifying
    @Query(value = "insert into post_images(post_id,image_id) values(?1,?2)",nativeQuery = true)
    void insertPostImage(Long postId, Long imageId);

}
