package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Donation;
import com.kindlink.kindLink.service.DonationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationController {

    private final DonationService service;
    public DonationController(DonationService service) { this.service = service; }

    @GetMapping
    public List<Donation> getAll() { return service.getAllDonations(); }

    @PostMapping
    public Donation create(@RequestBody Donation donation) { return service.createDonation(donation); }

    @GetMapping("/{id}")
    public Donation getById(@PathVariable Long id) { return service.getDonationById(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteDonation(id); }

    @GetMapping("/campaign/{campaignId}")
    public List<Donation> byCampaign(@PathVariable Long campaignId) { return service.getDonationsByCampaign(campaignId); }

    @GetMapping("/donor/{donorId}")
    public List<Donation> byDonor(@PathVariable Long donorId) { return service.getDonationsByDonor(donorId); }
}
