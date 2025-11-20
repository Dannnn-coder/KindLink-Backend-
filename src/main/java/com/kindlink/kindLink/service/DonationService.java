package com.kindlink.kindLink.service;

import com.kindlink.kindLink.entity.Donation;
import java.util.List;

public interface DonationService {
    List<Donation> getAllDonations();
    Donation createDonation(Donation donation);
    Donation getDonationById(Long id);
    void deleteDonation(Long id);
    List<Donation> getDonationsByCampaign(Long campaignId);
    List<Donation> getDonationsByDonor(Long donorId);
}
