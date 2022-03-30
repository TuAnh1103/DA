package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserRecoveryPasswordRequest;
import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.dto.request.user.UserUpdateInfoRequest;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    ResponseEntity<?> save(UserSaveRequest userSaveRequest);
    ResponseEntity<?> register(UserSaveRequest userSaveRequest);
    ResponseEntity<?> recoveryPassword(UserRecoveryPasswordRequest userRecoveryPasswordRequest);
    UserInfoResponse findById(Long id);
    Page<UserInfoResponse> findAll(Pageable pageable);
    User findOneById(Long id);
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
    void update(User user);

    List<User> findByHomeTown(Address address);

    List<User> findByCurrentCity(Address address);

    ResponseEntity<?> updateInfo(UserUpdateInfoRequest userUpdateInfoRequest);

    boolean isAdmin(Profile profile);
}
