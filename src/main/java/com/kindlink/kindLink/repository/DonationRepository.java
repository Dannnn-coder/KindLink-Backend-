package com.kindlink.kindLink.repository;

import com.kindlink.kindLink.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorUserId(Long donorId);
    List<Donation> findByCampaignCampaignId(Long campaignId);
}
