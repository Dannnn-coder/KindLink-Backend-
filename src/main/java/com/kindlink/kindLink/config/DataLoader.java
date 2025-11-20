package com.kindlink.kindLink.config;

import com.kindlink.kindLink.entity.Category;
import com.kindlink.kindLink.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadCategories(CategoryRepository categoryRepository) {
        return args -> {

            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Art & Culture Campaigns", "Campaigns related to arts, cultural heritage, and creative projects."));
                categoryRepository.save(new Category("Health & Medical Campaigns", "Campaigns that support health, treatment, and medical needs."));
                categoryRepository.save(new Category("Education Campaigns", "Campaigns that provide learning opportunities and scholarships."));
                categoryRepository.save(new Category("Disaster Relief Campaigns", "Campaigns intended for emergency response and disaster assistance."));
                categoryRepository.save(new Category("Environmental Campaigns", "Campaigns focused on climate action, conservation, and sustainability."));

                System.out.println("✔ Default categories loaded successfully!");
            } else {
                System.out.println("✔ Categories already exist — skipping preload.");
            }
        };
    }
}
