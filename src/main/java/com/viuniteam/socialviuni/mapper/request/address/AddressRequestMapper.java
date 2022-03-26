package com.viuniteam.socialviuni.mapper.request.address;

import com.viuniteam.socialviuni.dto.request.address.AddressSaveRequest;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface AddressRequestMapper extends Mapper<Address, AddressSaveRequest> {
}
