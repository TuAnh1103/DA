package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.dto.request.address.AddressSaveRequest;
import com.viuniteam.socialviuni.dto.response.address.AddressResponse;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.mapper.request.address.AddressRequestMapper;
import com.viuniteam.socialviuni.mapper.response.address.AddressResponseMapper;
import com.viuniteam.socialviuni.repository.AddressRepository;
import com.viuniteam.socialviuni.service.AddressService;
import com.viuniteam.socialviuni.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressRequestMapper addressRequestMapper;
    private final AddressResponseMapper addressResponseMapper;
    private final UserService userService;
    @Override
    public AddressResponse findOneById(Long id) {
        return addressResponseMapper.from(addressRepository.findOneById(id));
    }

    @Override
    public AddressResponse findOneByName(String name) {
        return addressResponseMapper.from(addressRepository.findOneByName(name));
    }

    @Override
    public List<AddressResponse> findAll() {
        return addressResponseMapper.from(addressRepository.findAll());
    }

    @Override
    public void save(AddressSaveRequest addressSaveRequest) {
        addressRepository.save(addressRequestMapper.to(addressSaveRequest));
    }

    @Override
    public void deleteById(Long id) {
        Address address = addressRepository.findOneById(id);
        if (address != null){
            // xoa het thong tin dia chi cua user trc moi duoc phep xoa dia chi
            List<User> userListHomeTown = userService.findByHomeTown(address);
            userListHomeTown.forEach(user -> {
                user.setHomeTown(null);
                userService.update(user);
            });
            List<User> userListCurrentCity= userService.findByCurrentCity(address);
            userListCurrentCity.forEach(user -> {
                user.setCurrentCity(null);
                userService.update(user);
            });
            addressRepository.deleteById(id);
        }
    }

    @Override
    public void update(Long id, AddressSaveRequest addressSaveRequest) {
        Address address = addressRepository.findOneById(id);
        if(address != null){
            address.setName(addressSaveRequest.getName());
            addressRepository.save(address);
        }
    }

    @Override
    public void saveList(List<AddressSaveRequest> addressSaveRequests) {
        addressSaveRequests.forEach(addressSaveRequest -> {
            this.save(addressSaveRequest);
        });
    }

}
