package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.Donation;
import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.repository.DonationRepository;
import com.kindlink.kindLink.repository.CampaignRepository;
import com.kindlink.kindLink.service.DonationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository repo;
    private final CampaignRepository campaignRepo;

    public DonationServiceImpl(DonationRepository repo, CampaignRepository campaignRepo) {
        this.repo = repo;
        this.campaignRepo = campaignRepo;
    }

    @Override
    public List<Donation> getAllDonations() { return repo.findAll(); }

    @Override
    public Donation createDonation(Donation donation) {
        // set date if missing
        if (donation.getDate() == null) donation.setDate(LocalDate.now());

        Donation saved = repo.save(donation);

        // update campaign current amount if campaign present
        if (donation.getCampaign() != null && donation.getAmount() != null) {
            Long campaignId = donation.getCampaign().getCampaignId();
            Optional<Campaign> opt = campaignRepo.findById(campaignId);
            if (opt.isPresent()) {
                Campaign c = opt.get();
                java.math.BigDecimal curr = c.getCurrentAmount() == null ? java.math.BigDecimal.ZERO : c.getCurrentAmount();
                c.setCurrentAmount(curr.add(donation.getAmount()));
                campaignRepo.save(c);
            }
        }
        return saved;
    }

    @Override
    public Donation getDonationById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void deleteDonation(Long id) { repo.deleteById(id); }

    @Override
    public List<Donation> getDonationsByCampaign(Long campaignId) {
        return repo.findByCampaignCampaignId(campaignId);
    }

    @Override
    public List<Donation> getDonationsByDonor(Long donorId) {
        return repo.findByDonorUserId(donorId);
    }
}
