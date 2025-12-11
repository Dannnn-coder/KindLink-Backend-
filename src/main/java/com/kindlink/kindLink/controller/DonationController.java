package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Donation;
import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.service.DonationService;
import com.kindlink.kindLink.dto.DonationRequest;
import com.kindlink.kindLink.dto.DonationResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "http://localhost:3000")
public class DonationController {

    private final DonationService service;
    public DonationController(DonationService service) { this.service = service; }

    @GetMapping
    public List<Donation> getAll() { return service.getAllDonations(); }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Donation donation) {
        Donation saved = service.createDonation(donation);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public Donation getById(@PathVariable Long id) { return service.getDonationById(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.deleteDonation(id); }

    @GetMapping("/campaign/{campaignId}")
    public List<Donation> byCampaign(@PathVariable Long campaignId) { return service.getDonationsByCampaign(campaignId); }

    @GetMapping("/donor/{donorId}")
    public ResponseEntity<?> byDonor(@PathVariable Long donorId) {
        try {
            List<Donation> donations = service.getDonationsByDonor(donorId);

            List<DonationResponse> resp = donations.stream().map(d -> {
                DonationResponse r = new DonationResponse();
                r.setDonationId(d.getDonationId());
                r.setAmount(d.getAmount());
                r.setDate(d.getDate());
                r.setPaymentMethod(d.getPaymentMethod());

                Campaign c = d.getCampaign();
                if (c != null) {
                    r.setCampaignId(c.getCampaignId());
                    r.setCampaignTitle(c.getTitle());
                }

                User u = d.getDonor();
                if (u != null) {
                    r.setDonorId(u.getUserId());
                    r.setDonorName(u.getFullname());
                    r.setDonorEmail(u.getEmail());
                }

                return r;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(resp);
        } catch (Exception ex) {
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("message", "Failed to load donations: " + ex.getMessage());
            return ResponseEntity.status(500).body(err);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFromDto(@RequestBody DonationRequest req) {
        try {
            if (req.getAmount() == null || req.getCampaignId() == null || req.getDonorId() == null) {
                Map<String, Object> err = new HashMap<>();
                err.put("success", false);
                err.put("message", "campaignId, donorId and amount are required");
                return ResponseEntity.badRequest().body(err);
            }

            Donation d = new Donation();
            d.setAmount(req.getAmount());
            d.setPaymentMethod(req.getPaymentMethod() != null ? req.getPaymentMethod() : "card");
            d.setDate(LocalDate.now());

            Campaign c = new Campaign();
            c.setCampaignId(req.getCampaignId());
            d.setCampaign(c);

            User u = new User();
            u.setUserId(req.getDonorId());
            d.setDonor(u);

            Donation saved = service.createDonation(d);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Donation recorded");
            response.put("donationId", saved.getDonationId());
            response.put("donation", saved);

            return ResponseEntity.status(201).body(response);
        } catch (Exception ex) {
            Map<String, Object> err = new HashMap<>();
            err.put("success", false);
            err.put("message", "Failed to create donation: " + ex.getMessage());
            return ResponseEntity.status(500).body(err);
        }
    }
}
