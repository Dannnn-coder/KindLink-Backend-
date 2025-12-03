package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.service.CampaignService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "http://localhost:3000")
public class CampaignController {

    private final CampaignService service;

    public CampaignController(CampaignService service) { this.service = service; }

    @GetMapping
    public List<Campaign> getAll() { return service.getAllCampaigns(); }

    @GetMapping("/{id}")
    public Campaign getById(@PathVariable Long id) { return service.getCampaignById(id); }

    @PutMapping("/{id}")
    public Campaign update(@PathVariable Long id, @RequestPart("campaign") Campaign campaign, 
                           @RequestPart(value = "image", required = false) MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            String uploadDir = "uploads/";
            try {
                File uploadFile = new File(uploadDir + fileName);
                uploadFile.getParentFile().mkdirs();
                image.transferTo(uploadFile);
                campaign.setImagePath(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return service.updateCampaign(id, campaign);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteCampaign(id); }

    @PostMapping(consumes = {"multipart/form-data"})
    public Campaign create(@RequestPart("campaign") Campaign campaign,
                           @RequestPart(value = "image", required = false) MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            String uploadDir = "uploads/";
            try {
                File uploadFile = new File(uploadDir + fileName);
                uploadFile.getParentFile().mkdirs();
                image.transferTo(uploadFile);
                campaign.setImagePath(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return service.createCampaign(campaign);
    }
}
