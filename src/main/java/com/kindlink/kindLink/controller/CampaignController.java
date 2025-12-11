package com.kindlink.kindLink.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.entity.Category;
import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.service.CampaignService;
import com.kindlink.kindLink.service.FileStorageService;
import com.kindlink.kindLink.repository.UserRepository;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CampaignController {

    private final CampaignService service;
    private final FileStorageService fileStorageService;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public CampaignController(CampaignService service,
                              FileStorageService fileStorageService,
                              UserRepository userRepository) {
        this.service = service;
        this.fileStorageService = fileStorageService;
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public List<Campaign> getAll() {
        return service.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public Campaign getById(@PathVariable Long id) {
        return service.getCampaignById(id);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, Object>> createWithImage(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("goalAmount") String goalAmount,
            @RequestParam("category") String categoryName,
            @RequestParam(value = "dates", required = false) String dates,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "creatorId", required = false) Long creatorId  // <-- NEW
    ) {
        try {
            Campaign campaign = new Campaign();
            campaign.setTitle(title);
            campaign.setDescription(description);
            campaign.setGoalAmount(new BigDecimal(goalAmount));
            campaign.setCurrentAmount(BigDecimal.ZERO);
            campaign.setDates(dates);

            if (image != null && !image.isEmpty()) {
                String fileName = fileStorageService.storeFile(image);
                campaign.setImageUrl("/api/files/" + fileName);
            }

            Category category = new Category();
            category.setName(categoryName);
            campaign.setCategories(List.of(category));

            if (creatorId != null) {
                System.out.println("Received creatorId: " + creatorId);
                Optional<User> maybeUser = userRepository.findById(creatorId);
                if (maybeUser.isPresent()) {
                    campaign.setCreator(maybeUser.get());
                } else {
                    Map<String, Object> error = new HashMap<>();
                    error.put("success", false);
                    error.put("message", "Creator (user) not found with id: " + creatorId);
                    return ResponseEntity.badRequest().body(error);
                }
            }

            Campaign savedCampaign = service.createCampaign(campaign);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("campaignId", savedCampaign.getCampaignId());
            response.put("message", "Campaign created successfully");
            response.put("campaign", savedCampaign);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Failed to create campaign: " + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping(consumes = {"application/json"})
    public Campaign create(@RequestBody Campaign campaign) {
        return service.createCampaign(campaign);
    }

    @PutMapping("/{id}")
    public Campaign update(@PathVariable Long id, @RequestBody Campaign campaign) {
        return service.updateCampaign(id, campaign);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCampaign(id);
    }
}
