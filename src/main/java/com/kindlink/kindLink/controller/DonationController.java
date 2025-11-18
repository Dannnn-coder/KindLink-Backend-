package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Donation;
import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.service.CampaignService;
import com.kindlink.kindLink.service.DonationService;
import com.kindlink.kindLink.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class DonationController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/donate/{id}")
    public String donatePage(@PathVariable Long id, Model model) {
        model.addAttribute("campaign", campaignService.get(id));
        return "donate";
    }

    @PostMapping("/donate/{id}")
    public String donate(
            @PathVariable Long id,
            @RequestParam BigDecimal amount,
            @RequestParam String method,
            @AuthenticationPrincipal UserDetails userDetails) {

        Donation donation = new Donation();
        donation.setCampaign(campaignService.get(id));
        donation.setDonationAmount(amount);
        donation.setPaymentMethod(method);

        User donor = userRepository.findByEmail(userDetails.getUsername());
        donation.setDonor(donor);

        donationService.donate(donation);

        return "redirect:/campaign/" + id;
    }
}
