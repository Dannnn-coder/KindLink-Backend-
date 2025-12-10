package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.Donation;
import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.repository.DonationRepository;
import com.kindlink.kindLink.repository.CampaignRepository;
import com.kindlink.kindLink.repository.UserRepository;
import com.kindlink.kindLink.service.DonationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public DonationServiceImpl(DonationRepository donationRepository,
                               CampaignRepository campaignRepository,
                               UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Donation> getDonationsByCampaign(Long campaignId) {
        return donationRepository.findByCampaignCampaignId(campaignId);
    }

    @Override
    public List<Donation> getDonationsByDonor(Long donorId) {
        // Use the join-fetch query to ensure campaign and donor objects are initialized
        return donationRepository.findByDonorUserIdWithCampaignAndDonor(donorId);
    }

    @Override
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

    /**
     * Creates a donation and updates the campaign's currentAmount atomically.
     */
    @Override
    @Transactional
    public Donation createDonation(Donation donation) {
        if (donation.getDate() == null) {
            donation.setDate(LocalDate.now());
        }

        // Defensive validations
        if (donation.getCampaign() == null || donation.getCampaign().getCampaignId() == null) {
            throw new IllegalArgumentException("Campaign id is required");
        }
        if (donation.getDonor() == null || donation.getDonor().getUserId() == null) {
            throw new IllegalArgumentException("Donor id is required");
        }
        if (donation.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }

        // Persist donation
        Donation savedDonation = donationRepository.save(donation);

        // Update campaign current amount
        Long campaignId = donation.getCampaign().getCampaignId();
        Optional<Campaign> optCampaign = campaignRepository.findById(campaignId);
        if (optCampaign.isPresent()) {
            Campaign campaign = optCampaign.get();
            BigDecimal current = campaign.getCurrentAmount() != null ? campaign.getCurrentAmount() : BigDecimal.ZERO;
            BigDecimal add = donation.getAmount() != null ? donation.getAmount() : BigDecimal.ZERO;
            campaign.setCurrentAmount(current.add(add));
            campaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Campaign not found: " + campaignId);
        }

        return savedDonation;
    }
}
