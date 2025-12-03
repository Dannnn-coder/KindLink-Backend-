package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:3000")
public class CampaignController {

    private final CampaignService service;
    public CampaignController(CampaignService service) { this.service = service; }

    @GetMapping
    public List<Campaign> getAll() { return service.getAllCampaigns(); }

    @PostMapping
    public Campaign create(@RequestBody Campaign campaign) { return service.createCampaign(campaign); }

    @GetMapping("/{id}")
    public Campaign getById(@PathVariable Long id) { return service.getCampaignById(id); }

    @PutMapping("/{id}")
    public Campaign update(@PathVariable Long id, @RequestBody Campaign campaign) { return service.updateCampaign(id, campaign); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteCampaign(id); }
}
