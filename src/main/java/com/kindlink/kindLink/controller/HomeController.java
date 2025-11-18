package com.kindlink.kindLink.controller;

import com.kindlink.kindLink.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("featuredCampaigns", campaignRepository.findAll());
        return "index";
    }
}
