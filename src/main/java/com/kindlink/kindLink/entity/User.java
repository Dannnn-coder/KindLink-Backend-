package com.kindlink.kindLink.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;  // ADD THIS IMPORT

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "fullname")
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role = "USER";

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    @JsonIgnore  // ADD THIS
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    @JsonIgnore  // ADD THIS
    private List<Donation> donations;

    public User() {}

    public User(String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() { 
        return userId; 
    }
    
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public String getFullname() { 
        return fullname; 
    }
    
    public void setFullname(String fullname) { 
        this.fullname = fullname; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getRole() { 
        return role; 
    }
    
    public void setRole(String role) { 
        this.role = role; 
    }

    public List<Campaign> getCampaigns() { 
        return campaigns; 
    }
    
    public void setCampaigns(List<Campaign> campaigns) { 
        this.campaigns = campaigns; 
    }

    public List<Donation> getDonations() { 
        return donations; 
    }
    
    public void setDonations(List<Donation> donations) { 
        this.donations = donations; 
    }
}