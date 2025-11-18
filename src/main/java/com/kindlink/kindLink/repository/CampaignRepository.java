package com.kindlink.kindLink.repository;

import com.kindlink.kindLink.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByTitleContainingIgnoreCase(String title);
}
