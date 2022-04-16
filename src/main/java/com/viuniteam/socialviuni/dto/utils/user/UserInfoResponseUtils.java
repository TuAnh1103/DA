package com.viuniteam.socialviuni.dto.utils.user;

import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.dto.utils.ResponseUtils;
import com.viuniteam.socialviuni.dto.utils.ResponseUtilsAdapter;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserInfoResponseMapper;
import com.viuniteam.socialviuni.utils.ListUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UserInfoResponseUtils extends ResponseUtilsAdapter<User, UserInfoResponse> {
    private final UserInfoResponseMapper userInfoResponseMapper;
    private final ImageReponseMapper imageReponseMapper;
    @Override
    public UserInfoResponse convert(User obj) {
        UserInfoResponse userInfoResponse = userInfoResponseMapper.from(obj);
        userInfoResponse.setAvatar(imageReponseMapper.from(ListUtils.getLast(obj.getAvatarImage())));
        userInfoResponse.setCover(imageReponseMapper.from(ListUtils.getLast(obj.getCoverImage())));
        return userInfoResponse;
    }

    @Override
    public List<UserInfoResponse> convert(List<User> obj) {
        List<UserInfoResponse> userInfoResponseList = new ArrayList<>();
        obj.stream().forEach(o->{
            UserInfoResponse userInfoResponse = userInfoResponseMapper.from(o);
            userInfoResponse.setAvatar(imageReponseMapper.from(ListUtils.getLast(o.getAvatarImage())));
            userInfoResponse.setCover(imageReponseMapper.from(ListUtils.getLast(o.getCoverImage())));
            userInfoResponseList.add(userInfoResponse);
        });
        return userInfoResponseList;
    }
}
