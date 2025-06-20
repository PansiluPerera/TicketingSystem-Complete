package com.ooprogramming.TicketingSystem.repositories;


import com.ooprogramming.TicketingSystem.entities.EventVendorEntity;
import com.ooprogramming.TicketingSystem.entities.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, Long> {
    VendorEntity findByVendorUserName(String vendorUserName);

    boolean existsByVendorUserName(String vendorUserName);





}


