package com.kindlink.kindLink.repository;

import com.kindlink.kindLink.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {}
