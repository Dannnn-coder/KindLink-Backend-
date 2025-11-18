package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.entity.Campaign;
import com.kindlink.kindLink.entity.User;
import com.kindlink.kindLink.repository.CategoryRepository;
import com.kindlink.kindLink.repository.UserRepository;
import com.kindlink.kindLink.service.CampaignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/campaigns")
    public String campaignList(
            @RequestParam(required = false) String search,
            Model model) {

        model.addAttribute("campaignList", campaignService.search(search));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("search", search);

        return "campaigns";
    }

    @GetMapping("/campaign/{id}")
    public String campaignDetails(@PathVariable Long id, Model model) {
        model.addAttribute("campaign", campaignService.get(id));
        return "campaign-details";
    }

    @GetMapping("/create-campaign")
    public String createCampaignPage(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("campaign", new Campaign());
        return "create-campaign";
    }

    @PostMapping("/create-campaign")
    public String createCampaign(
            Campaign campaign,
            @AuthenticationPrincipal UserDetails userDetails) {

        User creator = userRepository.findByEmail(userDetails.getUsername());
        campaign.setCreator(creator);

        campaignService.save(campaign);

        return "redirect:/campaigns";
    }
}
