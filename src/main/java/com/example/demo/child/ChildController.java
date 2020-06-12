package com.example.demo.child;

import com.example.demo.admin.Company;
import com.example.demo.admin.CompanyService;
import com.example.demo.admin.Donation;
import com.example.demo.admin.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChildController {

    private ChildService service;
    private DonationService donationService;
    @Autowired
    public void setDonationService(DonationService donationService) {
        this.donationService = donationService;
    }

    private CompanyService companyService;
    @Autowired
    public void setCompanyServiceService(CompanyService companyService) {
        this.companyService = companyService;
    }


    @Autowired
    public void setService(ChildService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "index";
    }

    @GetMapping("/index")
    public String reindex(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "index";
    }

    @GetMapping("/child")
    public String processAddStudentForm(Model model, @RequestParam int id) {
        model.addAttribute("child", service.getChildById(id));
        return "child";
    }

    @GetMapping("/child_list")
    public String childList(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "child_list";
    }

    @GetMapping("/details")
    public String childDetails(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "details";
    }

    @GetMapping("/boys")
    public String boys(Model model) {
        List<Child> children = service.getAllChildren();
        List<Child> boys = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getGender() == Child.Gender.male)
                boys.add(child);
        }

        model.addAttribute("children", boys);
        return "boys";
    }

    @GetMapping("/girls")
    public String girls(Model model) {
        List<Child> children = service.getAllChildren();
        List<Child> girls = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getGender() == Child.Gender.female)
                girls.add(child);
        }

        model.addAttribute("children", girls);
        return "girls";
    }

    @GetMapping("/illness")
    public String illness(Model model) {
        List<Child> children = service.getAllChildren();
        List<Child> illness = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getOriginalIllness() != null)
                illness.add(child);
        }
        model.addAttribute("children", illness);
        return "illness";
    }

    @GetMapping("/donation")
    public String donation(Model model) {
       model.addAttribute("company",companyService.getCompanyById(1));
        return "donation";
    }

    @GetMapping("/donations_list")
    public String donationsList(Model model) {
        model.addAttribute("donations", donationService.getAllDonations());
        return "donations_list";
    }


    @GetMapping("/about_project")
    public String aboutProject() {
        return "about_project";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("company",companyService.getCompanyById(1));
        return "contacts";
    }

    @GetMapping("/sponsors")
    public String sponsors(Model model, @RequestParam int id) {
        List<Donation> donations = donationService.getAllDonations();
        List<Donation> sponsors = new ArrayList<>();
        for (Donation donation : donations)
        {
            if (donation.getCode().equals(service.getChildById(id).getCode()))
            {
                sponsors.add(donation);

            }
        }
        model.addAttribute("users", sponsors);
        return "sponsors";
    }
}