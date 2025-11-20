package com.kindlink.kindLink.service;

import com.kindlink.kindLink.entity.Campaign;
import java.util.List;

public interface CampaignService {
    List<Campaign> getAllCampaigns();
    Campaign createCampaign(Campaign campaign);
    Campaign getCampaignById(Long id);
    Campaign updateCampaign(Long id, Campaign campaign);
    void deleteCampaign(Long id);
}
