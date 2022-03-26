package com.viuniteam.socialviuni.dto.utils.user;

import com.viuniteam.socialviuni.dto.response.user.UserAuthorResponse;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserAuthorResponseMapper;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserAuthorResponseUtils {
    private final UserAuthorResponseMapper userAuthorResponseMapper;
    private final ImageReponseMapper imageReponseMapper;
    public UserAuthorResponse convert(User author){
        UserAuthorResponse userAuthorResponse = userAuthorResponseMapper.from(author);
        userAuthorResponse.setAvatar(imageReponseMapper.from(ListUtils.getLast(author.getAvatarImage())));
        userAuthorResponse.setCover(imageReponseMapper.from(ListUtils.getLast(author.getCoverImage())));
        return userAuthorResponse;
    }
}
