package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserFilterRequest;
import com.viuniteam.socialviuni.dto.request.user.UserRecoveryPasswordRequest;
import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.dto.request.user.UserUpdateInfoRequest;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(UserSaveRequest userSaveRequest);
    void register(UserSaveRequest userSaveRequest);
    void recoveryPassword(UserRecoveryPasswordRequest userRecoveryPasswordRequest);
    UserInfoResponse findById(Long id);
    UserInfoResponse findByUsername(String username);
    Page<UserInfoResponse> findAll(Pageable pageable);

    Page<UserInfoResponse> search(UserFilterRequest userFilterRequest);

    User findOneById(Long id);
    User findOneByUsername(String username);
    User findByEmail(String email);
    void update(User user);

    List<User> findByHomeTown(Address address);

    List<User> findByCurrentCity(Address address);

    void updateInfo(UserUpdateInfoRequest userUpdateInfoRequest);

    boolean isAdmin(Profile profile);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
