package com.example.demo.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private CompanyRepository repository;

    @Autowired
    public void setRepository(CompanyRepository repository) {
        this.repository = repository;
    }

    public void saveCompany(Company company) {
        repository.save(company);
    }

    public void addCompany(Company company) {
        repository.save(company);
    }

    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    public Company getCompanyById(long id)
    {
        return repository.getOne(id);
    }

    public void deleteCompany (long id){
        repository.deleteById(id);
    }

    public void saveAll(List <Company> companies) {
        repository.saveAll(companies);
    }

}