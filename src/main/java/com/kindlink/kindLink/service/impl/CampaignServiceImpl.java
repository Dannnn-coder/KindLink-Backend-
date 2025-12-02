package com.kindlink.kindLink.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.entity.Category;
import com.kindlink.kindLink.entity.User;  // ADD THIS
import com.kindlink.kindLink.repository.CampaignRepository;
import com.kindlink.kindLink.repository.CategoryRepository;
import com.kindlink.kindLink.repository.UserRepository;
import com.kindlink.kindLink.service.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepo;
    private final CategoryRepository categoryRepo;
    private final UserRepository userRepository;  // ADD THIS

    // UPDATE CONSTRUCTOR
    public CampaignServiceImpl(CampaignRepository campaignRepo, 
                              CategoryRepository categoryRepo,
                              UserRepository userRepository) {  // ADD THIS
        this.campaignRepo = campaignRepo;
        this.categoryRepo = categoryRepo;
        this.userRepository = userRepository;  // ADD THIS
    }

    @Override
    public List<Campaign> getAllCampaigns() { 
        return campaignRepo.findAll(); 
    }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        // Set default values
        if (campaign.getCurrentAmount() == null) {
            campaign.setCurrentAmount(java.math.BigDecimal.ZERO);
        }
        
        // Handle categories
        if (campaign.getCategories() != null) {
            // Save or find existing categories
            for (Category category : campaign.getCategories()) {
                Optional<Category> existingCategory = categoryRepo.findByName(category.getName());
                if (existingCategory.isPresent()) {
                    category.setCategoryId(existingCategory.get().getCategoryId());
                } else {
                    categoryRepo.save(category);
                }
            }
        }
        
        return campaignRepo.save(campaign);
    }

    // ADD THIS HELPER METHOD
    public Campaign createCampaignWithCreator(Campaign campaign, Long creatorId) {
        // Find the user
        User creator = userRepository.findById(creatorId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + creatorId));
        
        campaign.setCreator(creator);
        campaign.setCurrentAmount(java.math.BigDecimal.ZERO);
        
        // Handle categories
        if (campaign.getCategories() != null) {
            for (Category category : campaign.getCategories()) {
                Optional<Category> existingCategory = categoryRepo.findByName(category.getName());
                if (existingCategory.isPresent()) {
                    category.setCategoryId(existingCategory.get().getCategoryId());
                } else {
                    categoryRepo.save(category);
                }
            }
        }
        
        return campaignRepo.save(campaign);
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return campaignRepo.findById(id).orElse(null);
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign campaign) {
        Optional<Campaign> opt = campaignRepo.findById(id);
        if (opt.isEmpty()) return null;
        
        Campaign existing = opt.get();
        existing.setTitle(campaign.getTitle());
        existing.setDescription(campaign.getDescription());
        existing.setDates(campaign.getDates());
        existing.setGoalAmount(campaign.getGoalAmount());
        existing.setCurrentAmount(campaign.getCurrentAmount());
        existing.setImageUrl(campaign.getImageUrl());
        
        // Only update creator if provided
        if (campaign.getCreator() != null) {
            existing.setCreator(campaign.getCreator());
        }
        
        // Update categories if provided
        if (campaign.getCategories() != null) {
            existing.setCategories(campaign.getCategories());
        }
        
        return campaignRepo.save(existing);
    }

    @Override
    public void deleteCampaign(Long id) { 
        campaignRepo.deleteById(id); 
    }
}