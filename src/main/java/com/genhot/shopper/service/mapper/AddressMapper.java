package com.genhot.shopper.service.mapper;

import com.genhot.shopper.domain.Address;
import com.genhot.shopper.domain.Customer;
import com.genhot.shopper.service.dto.AddressDTO;
import com.genhot.shopper.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "customerId")
    AddressDTO toDto(Address s);

    @Named("customerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDTO toDtoCustomerId(Customer customer);
}
