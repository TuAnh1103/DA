package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.Profile;
import com.viuniteam.socialviuni.dto.request.user.UserFilterRequest;
import com.viuniteam.socialviuni.dto.request.user.UserRecoveryPasswordRequest;
import com.viuniteam.socialviuni.dto.request.user.UserSaveRequest;
import com.viuniteam.socialviuni.dto.request.user.UserUpdateInfoRequest;
import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.dto.utils.user.UserInfoResponseUtils;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.Image;
import com.viuniteam.socialviuni.entity.Role;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.enumtype.RoleType;
import com.viuniteam.socialviuni.enumtype.SendCodeType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.exception.ObjectNotFoundException;
import com.viuniteam.socialviuni.mapper.request.user.UserRequestMapper;
import com.viuniteam.socialviuni.repository.AddressRepository;
import com.viuniteam.socialviuni.repository.RoleRepository;
import com.viuniteam.socialviuni.repository.UserRepository;
import com.viuniteam.socialviuni.repository.specification.UserSpecification;
import com.viuniteam.socialviuni.service.ImageService;
import com.viuniteam.socialviuni.service.MailService;
import com.viuniteam.socialviuni.service.PostService;
import com.viuniteam.socialviuni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private UserInfoResponseUtils userInfoResponseUtils;
//    @Autowired
//    private HandlingOffensive handlingOffensive;
    @Override
    public void save(UserSaveRequest userSaveRequest) {
        User user = userRequestMapper.to(userSaveRequest);
        // check thong tin co chua noi dung tho tuc hay khong
        //handlingOffensive.handling(userSaveRequest);

        // check thong tin dag ki da ton tai chua
        validateInfo(user);

        user.setActive(true);
        List<Long> roleIds = Arrays.asList(roleRepository.findOneByName(RoleType.ROLE_USER.getName()).getId());
        List<Role> roles = roleRepository.findAllByIdIn(roleIds);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        throw new OKException("Đăng ký tài khoản thành công");
    }

    public void validateInfo(User user){
        if(existsByUsername(user.getUsername()))
            throw new BadRequestException("Username đã tồn tại");

        if(existsByEmail(user.getEmail()))
            throw new BadRequestException("Email đã tồn tại");

        if(LocalDateTime.now().getYear() - user.getDob().getYear() < 12){
            throw new BadRequestException("Độ tuổi không đủ điều kiện tham gia");
        }
    }

    @Override
    public void register(UserSaveRequest userSaveRequest) {
        User user = userRequestMapper.to(userSaveRequest);
        // check thong tin co chua noi dung tho tuc hay khong
        //handlingOffensive.handling(userSaveRequest);

        // check thong tin dag ki da ton tai chua
        validateInfo(user);

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
        User user = findOneByUsername(userRecoveryPasswordRequest.getUsername());
        if(user==null) throw new ObjectNotFoundException("Tên tài khoản không tồn tại");
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
        return userInfoResponseUtils.convert(user);
    }
    @Override
    public UserInfoResponse findByUsername(String username) {
        Optional<User> users= userRepository.findByUsername(username);
        users.orElseThrow(()-> new ObjectNotFoundException("Tài khoản không tồn tại"));
        User user = users.get();
        return userInfoResponseUtils.convert(user);
    }


    @Override
    public Page<UserInfoResponse> findAll(Pageable pageable) {
        /*if(getIdUserName.getRoles().contains("ROLE_ADMIN")) {
            Page<User> users = userRepository.findAll(pageable.previousOrFirst());
            return users.map(userResponseMapper::from);
        }
        return null;*/

        /*List<UserInfoResponse> userInfoResponseList = new ArrayList<>();
        users.stream().forEach(user -> {
            userInfoResponseList.add(userInfoResponseUtils.convert(user));
        });
        return new PageImpl<>(userInfoResponseList, pageable, userInfoResponseList.size());
        */
        Page<User> users = userRepository.findAll(pageable.previousOrFirst());
        return users.map(userInfoResponseUtils::convert);
    }


    @Override
    public Page<UserInfoResponse> search(UserFilterRequest userFilterRequest) {
        PageRequest pageRequest = PageRequest.of(userFilterRequest.getIndex(), userFilterRequest.getSize());
        Page<User> users = userRepository.findAll(UserSpecification.filterAll(userFilterRequest),pageRequest);
        if(isAdmin(profile))
            return users.map(userInfoResponseUtils::convert);
        else {
            List<UserInfoResponse> userInfoResponseList =  users.stream()
                    .filter(user -> user.isActive())
                    .map(userInfoResponseUtils::convert)
                    .collect(Collectors.toList());
            return new PageImpl<>(userInfoResponseList,pageRequest, userInfoResponseList.size());
        }
    }

    @Override
    public User findOneById(Long id) {
        return userRepository.findOneById(id);
    }


    @Override
    public User findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findOneByEmail(email);
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
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findOneByUsername(username);
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
