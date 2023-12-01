package com.mingyuchoo.pgsql.biz.address.repository;

import com.mingyuchoo.pgsql.biz.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Address findById(long id);
    // List<Address> findByFields(String fields);
    // List<Address> findAll();
}
