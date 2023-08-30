package com.genhot.shopper.service.impl;

import com.genhot.shopper.domain.Address;
import com.genhot.shopper.repository.AddressRepository;
import com.genhot.shopper.service.AddressService;
import com.genhot.shopper.service.dto.AddressDTO;
import com.genhot.shopper.service.mapper.AddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Mono<AddressDTO> save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        return addressRepository.save(addressMapper.toEntity(addressDTO)).map(addressMapper::toDto);
    }

    @Override
    public Mono<AddressDTO> update(AddressDTO addressDTO) {
        log.debug("Request to update Address : {}", addressDTO);
        return addressRepository.save(addressMapper.toEntity(addressDTO)).map(addressMapper::toDto);
    }

    @Override
    public Mono<AddressDTO> partialUpdate(AddressDTO addressDTO) {
        log.debug("Request to partially update Address : {}", addressDTO);

        return addressRepository
            .findById(addressDTO.getId())
            .map(existingAddress -> {
                addressMapper.partialUpdate(existingAddress, addressDTO);

                return existingAddress;
            })
            .flatMap(addressRepository::save)
            .map(addressMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAllBy(pageable).map(addressMapper::toDto);
    }

    public Mono<Long> countAll() {
        return addressRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<AddressDTO> findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id).map(addressMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        return addressRepository.deleteById(id);
    }
}
