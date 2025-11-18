package com.kindlink.kindLink.service;

import com.kindlink.kindLink.entity.Campaign;

import java.util.List;

public interface CampaignService {
    Campaign save(Campaign campaign);
    Campaign get(Long id);
    List<Campaign> search(String title);
}
