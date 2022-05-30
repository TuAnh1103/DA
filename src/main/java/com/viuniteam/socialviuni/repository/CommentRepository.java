package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Comment;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//@Transactional
public interface CommentRepository extends JpaRepository<Comment,Long> {
    void deleteById(Long id);
    Comment findOneById(Long id);
    Comment findTop1ByPostOrderByCreatedDateDesc(Post post);
    Long countByPost(Post post);

    @Query(value = "select count(*) from (select count(cmt.id) from comment cmt where cmt.post_id=:postId group by cmt.user_id) t", nativeQuery = true) //select count (cmt.id) from Comment cmt where cmt.post=:post group by cmt.user
    Long countByPostGroupByUser(@Param("postId") Long postId);

    Page<Comment> findAllByPostOrderByIdDesc(Post post, Pageable pageable);
    @Modifying
    @Query(value = "delete comment from comment comment" +
            " inner join post post on post.id = comment.post_id" +
            " inner join user author on author.id = post.user_id"+
            " where post.id = :postId and comment.user_id = :userId" +
            " and comment.id = :commentId and author.active = true", nativeQuery = true)
    void deleteComment(@Param("postId") Long postId, @Param("userId") Long userId, @Param("commentId") Long commentId);
}
