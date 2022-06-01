package com.viuniteam.socialviuni.repository.specification;

import com.viuniteam.socialviuni.dto.request.user.UserFilterRequest;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class UserSpecification {
    public static Specification<User> filterAll(UserFilterRequest filter){
        return Specification.where(withUsername(filter.getKeyword()))
                .or(withLastName(filter.getKeyword()))
                .or(withFirstName(filter.getKeyword()));
    }
    public static Specification<User> withUsername(String username){
        if(!StringUtils.hasLength(username)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"),"%"+username+"%"));
    }

    public static Specification<User> withFirstName(String firstName){
        if(!StringUtils.hasLength(firstName)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("firstName"),"%"+firstName+"%"));
    }
    public static Specification<User> withLastName(String lastName){
        if(!StringUtils.hasLength(lastName)) return null;
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("lastName"),"%"+lastName+"%"));
    }


//    public static Specification<User> withUsername(String username){
//        if(!StringUtils.hasLength(username)) return null;
//        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"),username));
//    }

}
