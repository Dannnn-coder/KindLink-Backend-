package com.kindlink.kindLink.repository;

import com.kindlink.kindLink.entity.CampaignCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampaignCategoryRepository extends JpaRepository<CampaignCategory, Long> {
    List<CampaignCategory> findByCampaignCampaignId(Long campaignId);
    List<CampaignCategory> findByCategoryCategoryId(Long categoryId);
}
