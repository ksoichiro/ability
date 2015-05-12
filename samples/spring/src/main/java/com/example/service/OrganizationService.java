package com.example.service;

import com.example.domain.Organization;
import com.example.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository serviceRepository;

    public Iterable<Organization> findAll() {
        return serviceRepository.findAll();
    }
}
