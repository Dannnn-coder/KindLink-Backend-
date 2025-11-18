package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.Donation;
import com.kindlink.kindLink.repository.DonationRepository;
import com.kindlink.kindLink.repository.CampaignRepository;
import com.kindlink.kindLink.service.DonationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public Donation donate(Donation donation) {

        donationRepository.save(donation);

        donation.getCampaign().setCurrentAmount(
                donation.getCampaign().getCurrentAmount().add(donation.getDonationAmount())
        );

        campaignRepository.save(donation.getCampaign());

        return donation;
    }
}
