package com.example.demo.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    private DonationRepository repository;

    @Autowired
    public void setRepository(DonationRepository repository) {
        this.repository = repository;
    }

    public void saveDonation(Donation donation) {
        repository.save(donation);
    }

    public void addDonation(Donation donation) {
        repository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return repository.findAll();
    }

    public Donation getDonationById(long id)
    {
        return repository.getOne(id);
    }

    public void deleteDonation (long id){
        repository.deleteById(id);
    }

    public void saveAll(List <Donation> donations) {
        repository.saveAll(donations);
    }

}