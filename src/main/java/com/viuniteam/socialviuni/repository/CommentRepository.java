package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

//@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long> {
    void deleteById(Long id);
    Comment findOneById(Long id);
    Long countByPost(Post post);

    @Modifying
    @Query(value = "delete Comment from Comment comment" +
            " inner join Post post on post.id = comment.post_id" +
            " inner join User author on author.id = post.user_id"+
            " where post.id = :postId and comment.user_id = :userId" +
            " and comment.id = :commentId and author.active = true", nativeQuery = true)
    void deleteComment(@Param("postId") Long postId, @Param("userId") Long userId, @Param("commentId") Long commentId);
}
