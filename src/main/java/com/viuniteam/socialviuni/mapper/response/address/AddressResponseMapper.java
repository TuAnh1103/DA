package com.viuniteam.socialviuni.mapper.response.address;

import com.viuniteam.socialviuni.dto.response.address.AddressResponse;
import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.mapper.Mapper;

@org.mapstruct.Mapper(componentModel = "spring")
public interface AddressResponseMapper extends Mapper<Address, AddressResponse> {
}
