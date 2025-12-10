package com.kindlink.kindLink.repository;

import com.kindlink.kindLink.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE d.donor.userId = :donorId")
    List<Donation> findByDonorUserId(@Param("donorId") Long donorId);

    @Query("SELECT d FROM Donation d WHERE d.campaign.campaignId = :campaignId")
    List<Donation> findByCampaignCampaignId(@Param("campaignId") Long campaignId);

    @Query("SELECT d FROM Donation d LEFT JOIN FETCH d.campaign c LEFT JOIN FETCH d.donor u WHERE u.userId = :donorId")
    List<Donation> findByDonorUserIdWithCampaignAndDonor(@Param("donorId") Long donorId);
}
