package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.repository.CampaignRepository;
import com.kindlink.kindLink.service.CampaignService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository repo;

    public CampaignServiceImpl(CampaignRepository repo) { this.repo = repo; }

    @Override
    public List<Campaign> getAllCampaigns() { return repo.findAll(); }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        if (campaign.getCurrentAmount() == null) campaign.setCurrentAmount(java.math.BigDecimal.ZERO);
        return repo.save(campaign);
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign campaign) {
        Optional<Campaign> opt = repo.findById(id);
        if (opt.isEmpty()) return null;
        Campaign existing = opt.get();
        existing.setTitle(campaign.getTitle());
        existing.setDescription(campaign.getDescription());
        existing.setDates(campaign.getDates());
        existing.setGoalAmount(campaign.getGoalAmount());
        existing.setCurrentAmount(campaign.getCurrentAmount());
        existing.setCreator(campaign.getCreator());
        return repo.save(existing);
    }

    @Override
    public void deleteCampaign(Long id) { repo.deleteById(id); }
}
