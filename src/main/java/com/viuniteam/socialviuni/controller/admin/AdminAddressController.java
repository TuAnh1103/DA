package com.viuniteam.socialviuni.controller.admin;

import com.viuniteam.socialviuni.dto.request.address.AddressSaveRequest;
import com.viuniteam.socialviuni.dto.response.address.AddressResponse;
import com.viuniteam.socialviuni.service.AddressService;
import com.viuniteam.socialviuni.utils.Keyword;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/address")
@AllArgsConstructor
public class AdminAddressController {
    private final AddressService addressService;
    @PostMapping
    public void addAddress(@Valid @RequestBody AddressSaveRequest addressSaveRequest){
        addressService.save(addressSaveRequest);
    }
    @PostMapping("/addlist")
    public void addListAddress(@Valid @RequestBody List<AddressSaveRequest> addressSaveRequestList){
        addressService.saveList(addressSaveRequestList);
    }
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable("id") Long id){
        addressService.deleteById(id);
    }

    @GetMapping("/{id}")
    public AddressResponse getById(@PathVariable("id") Long id){
        return addressService.findOneById(id);
    }
    @GetMapping("/name")
    public AddressResponse getByName(@RequestBody Keyword keyword){
        return addressService.findOneByName(keyword.getKeyword());
    }

    @GetMapping("/getall")
    public List<AddressResponse> getAll(){
        return addressService.findAll();
    }

    @PutMapping("/{id}")
    public void updateAddress(@PathVariable("id") Long  id,@RequestBody AddressSaveRequest addressSaveRequest){
        addressService.update(id,addressSaveRequest);
    }
}
