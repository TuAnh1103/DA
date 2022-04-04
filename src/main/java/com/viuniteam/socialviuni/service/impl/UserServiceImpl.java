package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserRecoveryPasswordRequest;
import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.dto.request.user.UserUpdateInfoRequest;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.entity.Role;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.SendCodeType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.request.user.UserRequestMapper;
import com.viuniteam.socialviuni.mapper.response.image.ImageReponseMapper;
import com.viuniteam.socialviuni.mapper.response.user.UserInfoResponseMapper;
import com.viuniteam.socialviuni.repository.AddressRepository;
import com.viuniteam.socialviuni.repository.RoleRepository;
import com.viuniteam.socialviuni.repository.UserRepository;
import com.viuniteam.socialviuni.service.*;
import com.viuniteam.socialviuni.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserInfoResponseMapper userResponseMapper;
    @Autowired
    private UserRequestMapper userRequestMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Profile profile;
    @Autowired
    private MailService mailService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PostService postService;

    @Autowired
    private ImageReponseMapper imageReponseMapper;

    @Override
    public void save(UserSaveRequest userSaveRequest) {
        User user = userRequestMapper.to(userSaveRequest);
        user.setActive(true);
        List<Long> roleIds = Arrays.asList(roleRepository.findOneByName("ROLE_USER").getId());
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        user.setRoles(roles);
        Optional<User> checkUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> checkEmail = userRepository.findByEmail(user.getEmail());

        if(!checkUsername.isEmpty())
            throw new BadRequestException("Username đã tồn tại");

        if(!checkEmail.isEmpty())
            throw new BadRequestException("Email đã tồn tại");

        if(LocalDateTime.now().getYear() - user.getDob().getYear() < 12){
            throw new BadRequestException("Độ tuổi không đủ điều kiện tham gia");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        throw new OKException("Đăng ký tài khoản thành công");
    }

    @Override
    public void register(UserSaveRequest userSaveRequest) {
        User user = userRequestMapper.to(userSaveRequest);
        Optional<User> checkUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> checkEmail = userRepository.findByEmail(user.getEmail());

        if(!checkUsername.isEmpty())
            throw new BadRequestException("Username đã tồn tại");

        if(!checkEmail.isEmpty())
            throw new BadRequestException("Email đã tồn tại");

        if(LocalDateTime.now().getYear() - user.getDob().getYear() < 12){
            throw new BadRequestException("Độ tuổi không đủ điều kiện tham gia");
        }

        if(userSaveRequest.getCode() != null){ // gửi kèm code
            if(mailService.hasCode(userSaveRequest.getEmail(),userSaveRequest.getCode())){
                mailService.deleteByEmail(userSaveRequest.getEmail());
                save(userSaveRequest);
            }
            throw new BadRequestException("Mã xác nhận không chính xác");
        }
        mailService.sendCode(userSaveRequest.getEmail(), userSaveRequest.getUsername(), SendCodeType.REGISTER);
    }

    @Override
    public void recoveryPassword(UserRecoveryPasswordRequest userRecoveryPasswordRequest) {
        Optional<User> users = userRepository.findByUsername(userRecoveryPasswordRequest.getUsername());
        users.orElseThrow(()-> new ObjectNotFoundException("Tên tài khoản không tồn tại"));
        User user = users.get();
        String email = user.getEmail();
        String code = userRecoveryPasswordRequest.getCode();
        String password = userRecoveryPasswordRequest.getPassword();
        if(code!=null){
            if(mailService.hasCode(email,code)){
                if(password != null){
                    user.setPassword(passwordEncoder.encode(password));
                    update(user);
                    mailService.deleteByEmail(email);
                    throw new OKException("Khôi phục mật khẩu thành công");
                }
                throw new OKException("Mã xác nhận chính xác");
            }
            else
                throw new BadRequestException("Mã xác nhận không chính xác");
        }
        mailService.sendCode(email, userRecoveryPasswordRequest.getUsername(), SendCodeType.RECOVERY);
    }

    @Override
    public UserInfoResponse findById(Long id) {
        Optional<User> users= userRepository.findById(id);
        users.orElseThrow(()-> new ObjectNotFoundException("Tài khoản không tồn tại"));
        User user = users.get();
        List<Image> avatarImages = user.getAvatarImage();
        List<Image> coverImages = user.getCoverImage();
        UserInfoResponse userInfoResponse = userResponseMapper.from(user);

        userInfoResponse.setAvatar(imageReponseMapper.from(ListUtils.getLast(avatarImages)));
        userInfoResponse.setCover(imageReponseMapper.from(ListUtils.getLast(coverImages)));
        return userInfoResponse;
    }

    @Override
    public Page<UserInfoResponse> findAll(Pageable pageable) {
        /*if(getIdUserName.getRoles().contains("ROLE_ADMIN")) {
            Page<User> users = userRepository.findAll(pageable.previousOrFirst());
            return users.map(userResponseMapper::from);
        }
        return null;*/
        Page<User> users = userRepository.findAll(pageable.previousOrFirst());
        List<UserInfoResponse> userInfoResponseList = new ArrayList<>();
        users.stream().forEach(user -> {
            UserInfoResponse userInfoResponse = userResponseMapper.from(user);
            userInfoResponse.setAvatar(imageReponseMapper.from(ListUtils.getLast(user.getAvatarImage())));
            userInfoResponse.setCover(imageReponseMapper.from(ListUtils.getLast(user.getCoverImage())));
            userInfoResponseList.add(userInfoResponse);
        });
        return new PageImpl<>(userInfoResponseList, pageable, userInfoResponseList.size());
    }

    @Override
    public User findOneById(Long id) {
        return userRepository.findOneById(id);
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> findByHomeTown(Address address) {
        return userRepository.findByHomeTown(address);
    }

    @Override
    public List<User> findByCurrentCity(Address address) {
        return userRepository.findByCurrentCity(address);
    }

//    update  thong tin ca nhan user
    @Override
    public void updateInfo(UserUpdateInfoRequest userUpdateInfoRequest) {
        User user = findOneById(profile.getId());
        String lastName = userUpdateInfoRequest.getLastName();
        String firstName = userUpdateInfoRequest.getFirstName();
        String dob = userUpdateInfoRequest.getDob();
        String bio = userUpdateInfoRequest.getBio();
        String gender = userUpdateInfoRequest.getGender();
        Long idHomeTown = userUpdateInfoRequest.getIdHomeTown();
        Long idCurrentCity = userUpdateInfoRequest.getIdCurrentCity();
        Long idAvatarImage = userUpdateInfoRequest.getIdAvatarImage();
        Long idCoverImage = userUpdateInfoRequest.getIdCoverImage();


        if(lastName!=null)
            user.setLastName(lastName);

        if(firstName!=null)
            user.setFirstName(firstName);

        if(dob!=null)
            user.setDob(LocalDate.parse(dob));

        if(bio!=null)
            user.setBio(bio);

        if(gender!=null){
            if(gender.equals("1"))
                user.setGender(true);
            else if(gender.equals("0"))
                user.setGender(false);
        }

        if(idHomeTown!=null){
            Address homeTown = addressRepository.findOneById(idHomeTown);
            if(homeTown!=null)
                user.setHomeTown(homeTown);
        }

        if(idCurrentCity!=null){
            Address currentCity  = addressRepository.findOneById(idCurrentCity);
            if(currentCity!=null)
                user.setCurrentCity(currentCity);
        }

        if(idAvatarImage!=null){
            Image image = imageService.findOneById(idAvatarImage);
            if(image != null){
                user.getAvatarImage().add(image);
                List<Image> newAvatar = new ArrayList<>();
                newAvatar.add(image);
                String genderUser = user.isGender() ? "anh" : "cô";
                postService.autoCreatePost(String.format("%s đã thay đổi ảnh đại diện của %s ấy",profile.getFirstName(),genderUser),newAvatar);
            }

        }
        if(idCoverImage!=null){
            Image image = imageService.findOneById(idCoverImage);
            if(image!=null){
                user.getCoverImage().add(image);
                List<Image> newCover = new ArrayList<>();
                newCover.add(image);
                String genderUser = user.isGender() ? "anh" : "cô";
                postService.autoCreatePost(String.format("%s đã thay đổi ảnh bìa của %s ấy",profile.getFirstName(),genderUser),newCover);
            }
        }
        update(user);

        throw new OKException("Cập nhật thông tin thành công");
    }

    @Override
    public boolean isAdmin(Profile profile) {
        return profile.getRoles().contains("ROLE_ADMIN");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
        //check tai khoan co hoat dong thi moi cho dang  nhap
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), user.isActive(),
        true, true, true,
        mapRolesToAuthorities(user.getRoles()));
    }

    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
