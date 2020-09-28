package com.mingyuchoo.pgsqldemo.repository;

import com.mingyuchoo.pgsqldemo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Address findById(long id);
    // List<Address> findByFields(String fields);
    // List<Address> findAll();
}
