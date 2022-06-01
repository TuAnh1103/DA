package com.viuniteam.socialviuni.repository.specification;

import com.viuniteam.socialviuni.dto.request.post.PostFilterRequest;
import com.viuniteam.socialviuni.entity.Post;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class PostSpecification {
    public static Specification<Post> filterAll(PostFilterRequest filter){
        return Specification.where(withContent(filter.getKeyword()));
    }

    public static Specification<Post> withContent(String content){
        if(!StringUtils.hasLength(content)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("content"),"%"+content+"%"));
    }
}
