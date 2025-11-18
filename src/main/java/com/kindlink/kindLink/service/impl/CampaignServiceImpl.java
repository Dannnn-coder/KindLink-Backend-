package com.kindlink.kindLink.service.impl;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.repository.CampaignRepository;
import com.kindlink.kindLink.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public Campaign save(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
    public Campaign get(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
    }

    @Override
    public List<Campaign> search(String title) {
        if (title == null || title.isBlank()) {
            return campaignRepository.findAll();
        }
        return campaignRepository.findByTitleContainingIgnoreCase(title);
    }
}
