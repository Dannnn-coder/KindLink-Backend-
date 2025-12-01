package com.kindlink.kindLink.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.service.CampaignService;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CampaignController {

    private final CampaignService service;
    
    public CampaignController(CampaignService service) { 
        this.service = service; 
    }

    // Public - anyone can view campaigns
    @GetMapping
    public List<Campaign> getAll() { 
        return service.getAllCampaigns(); 
    }

    // Public - anyone can view single campaign
    @GetMapping("/{id}")
    public Campaign getById(@PathVariable Long id) { 
        return service.getCampaignById(id); 
    }

    // Protected - only authenticated users can create
    @PostMapping
    public Campaign create(@RequestBody Campaign campaign) { 
        return service.createCampaign(campaign); 
    }

    // Protected - only creator can update
    @PutMapping("/{id}")
    public Campaign update(@PathVariable Long id, @RequestBody Campaign campaign) { 
        return service.updateCampaign(id, campaign); 
    }

    // Protected - only creator can delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { 
        service.deleteCampaign(id); 
    }
}