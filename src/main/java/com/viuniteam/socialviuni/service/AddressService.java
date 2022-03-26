package com.viuniteam.socialviuni.service;

import com.viuniteam.socialviuni.dto.request.address.AddressSaveRequest;
import com.viuniteam.socialviuni.dto.response.address.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse findOneById(Long id);
    AddressResponse findOneByName(String name);
    List<AddressResponse> findAll();
    void save(AddressSaveRequest addressSaveRequest);
    void deleteById(Long id);
    void update(Long id,AddressSaveRequest addressSaveRequest);
    void saveList(List<AddressSaveRequest> addressSaveRequests);
}
