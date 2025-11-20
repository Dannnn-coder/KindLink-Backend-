package com.kindlink.kindLink.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "campaign_categories")
public class CampaignCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public CampaignCategory() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Campaign getCampaign() { return campaign; }
    public void setCampaign(Campaign campaign) { this.campaign = campaign; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
