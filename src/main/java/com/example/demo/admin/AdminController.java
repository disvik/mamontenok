package com.example.demo.admin;

import com.example.demo.child.Child;
import com.example.demo.child.ChildService;
import com.example.demo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AdminController {

    private ChildService service;
    private LocalDate tempDate;
    private ManagerService managerService;
    private UserService userService;
    private DonationService donationService;
    private MyUserDetailsService userDetailsService;
    private FavouriteService favouriteService;
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setCompanyServiceService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public void setService(ChildService service) {
        this.service = service;
    }

    @Autowired
    public void setUserService(UserService service) {
        this.userService = service;
    }

    @Autowired
    public void setFavouriteService(FavouriteService service) {
        this.favouriteService = service;
    }


    @Autowired
    public void setManagerService(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Autowired
    public void setDonationService(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping("/login")
    public String login(HttpSession session){
        return "login";
    }
    /*@GetMapping("/admin/index")
    public String index(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "admin/index";
    }*/

    @GetMapping("/admin/child_list")
    public String adminChildList(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "admin/child_list";
    }

    @GetMapping("/admin/managers")
    public String adminManagersList(Model model) {
        List<User> managers = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        for (User user : users)
        {
            if (user.getRoles().contains("ROLE_MANAGER"))
            {
                managers.add(user);
            }

        }
        model.addAttribute("managers", managers);
        return "admin/managers";
    }

    @GetMapping("/admin/add_manager")
    public String addManager(Model model) {
        model.addAttribute("user", new User());
        return "admin/add_manager";
    }

    @PostMapping("/admin/add_manager")
    public String processAddManager(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/add_manager";
        }
        user.setActive(true);
        user.setRoles("ROLE_MANAGER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/managers";
    }

    @GetMapping("/admin/donations_week")
    public String adminDonationWeek(Model model) {
        List<Donation> donations = donationService.getAllDonations();
        List<Donation> donationsWeek = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate week = now.minusWeeks(1);

        for (Donation donation : donations) {
            if (donation.getIsDoneDate().isAfter(week) && (donation.getIsDoneDate().isBefore(now) || donation.getIsDoneDate().equals(now)))
            {
                donationsWeek.add(donation);
            }
        }
        model.addAttribute("donations", donationsWeek);
        return "admin/donations_week";
    }

    @GetMapping("/admin/donations_month")
    public String adminDonationMonth(Model model) {
        List<Donation> donations = donationService.getAllDonations();
        List<Donation> donationsWeek = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate month = now.minusMonths(1);

        for (Donation donation : donations) {
            if (donation.getIsDoneDate().isAfter(month) && (donation.getIsDoneDate().isBefore(now) || donation.getIsDoneDate().equals(now)))
            {
                donationsWeek.add(donation);
            }
        }
        model.addAttribute("donations", donationsWeek);
        return "admin/donations_month";
    }

    @GetMapping("/admin/donations_list")
    public String adminDonationList(Model model) {
        model.addAttribute("donations", donationService.getAllDonations());
        return "admin/donations_list";
    }

    @GetMapping("/admin/add_donation")
    public String addDonation(Model model) {
        model.addAttribute("donation", new Donation());

        return "admin/add_donation";
    }

    @GetMapping("/admin/edit_donation")
    public String editDonation(Model model, @RequestParam int id) {
        model.addAttribute("donation", donationService.getDonationById(id));
        tempDate = donationService.getDonationById(id).getIsAcceptedDate();
        donationService.deleteDonation(id);
        return "admin/edit_donation";
    }

    @PostMapping("/admin/edit_donation")
    public String editPostDonation(@Valid Donation donation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit_donation";
        }
        LocalDate date = LocalDate.now();
        donation.setIsDoneDate(date);
        donation.setIsAcceptedDate(tempDate);
        donationService.saveDonation(donation);
        return "redirect:/admin/donations_list";
    }



    @PostMapping("/admin/add_donation")
    public String processAddDonation(@Valid Donation donation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            donation.setStatus("В обработке");
            return "admin/add_donation";
        }
        LocalDate date = LocalDate.now();
        donation.setIsAcceptedDate(date);
        donation.setStatus("В обработке");
        donationService.saveDonation(donation);
        return "redirect:/admin/donations_list";
    }

    @GetMapping("/admin/about_project")
    public String adminAboutProject() {
        return "admin/about_project";
    }

    @GetMapping("/admin/child")
    public String processAddStudentForm(Model model, @RequestParam int id) {
        model.addAttribute("child", service.getChildById(id));
        return "admin/child";
    }

    @GetMapping("/admin/my_account")
    public String myAccount(Model model, Principal principal) {
        final MyUserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        long id = user.getUser_id();
        model.addAttribute("user", userService.getUserById(id));
        return "admin/my_account";
    }

    @PostMapping("/admin/my_account")
    public String myAccount(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/my_account";
        }
        userService.saveUser(user);
        return "admin/my_account";
    }

    @PostMapping("/admin/child")
    public String processAddFavourite(Model model, @RequestParam long id, Principal principal) {
        Child child = service.getChildById(id);
        Favourite favourite = new Favourite(child.getId(), child.getFirstName(), child.getYearOfBirth(),
                child.getGender(), child.getCode(), child.getHobby(), child.getPhotoName(), child.getIllness());
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        long user_id = userDetails.getUser_id();
        User user = userService.getUserById(user_id);
        favouriteService.addFavourite(favourite, user_id);
        userService.saveUser(user);
        model.addAttribute("favourites", user.getFavourites());
        return "admin/favourites";
    }

    @GetMapping("/admin/add_child")
    public String addChild(Model model) {
        model.addAttribute("child", new Child());
        return "admin/add_child";
    }

    @PostMapping("/admin/add_child")
    public String processAddStudentForm(@Valid Child child, BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "admin/add_child";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get("/Users/disvik/Documents/ONPU/2course2sem/oop/course_work/src/main/resources/static/images/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            child.setPhotoName("../images/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(child.getIllness().length()<5)
            child.setIllness(null);
        service.saveChild(child);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/edit_child")
    public String editChild(Model model, @RequestParam int id) {
        model.addAttribute("child", service.getChildById(id));
        service.deleteChild(id);
        return "admin/edit_child";
    }

    @PostMapping("/admin/edit_child")
    public String editPostChild(@Valid Child child, BindingResult bindingResult, @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "admin/edit_child";
        }
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get("/Users/disvik/Documents/ONPU/2course2sem/oop/course_work/src/main/resources/static/images/" + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            child.setPhotoName("../images/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(child.getIllness().length()<5)
            child.setIllness(null);
        service.saveChild(child);
        return "redirect:/admin/child_list";
    }

    @GetMapping("/admin/delete_child")
    public String deleteChild(@RequestParam int id) {
        service.deleteChild(id);
        return "admin/child_list";
    }

    @GetMapping("/admin/favourites")
    public String userFavourites(Model model, Principal principal) {
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        long user_id = userDetails.getUser_id();
        User user = userService.getUserById(user_id);
        model.addAttribute("favourites", user.getFavourites());
        return "admin/favourites";
    }

    @GetMapping("/admin/users_list")
    public String userUsersList(Model model) {
        List<User> onlyUsers = new ArrayList<>();
        List<User> users = userService.getAllUsers();
        for (User user : users)
        {
            if (user.getRoles().contains("ROLE_USER"))
            {
                onlyUsers.add(user);
            }

        }
        model.addAttribute("users", onlyUsers);
        return "admin/users_list";
    }

    @GetMapping("/admin/add_user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/add_user";
    }

    @PostMapping("/admin/add_user")
    public String processRegistration(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/add_user";
        }
        user.setActive(true);
        user.setRoles("ROLE_USER");
        user.setPassword("1234");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users_list";
    }

    @GetMapping("/admin/edit_user")
    public String editUser(Model model, @RequestParam int id) {
        model.addAttribute("user", userService.getUserById(id));
        userService.deleteUser(id);
        return "admin/edit_user";
    }

    @PostMapping("/admin/edit_user")
    public String processEditUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit_user";
        }
        user.setActive(true);
        user.setRoles("ROLE_USER");
        user.setPassword("1234");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/users_list";
    }


    @GetMapping("/admin/details")
    public String adminChildDetails(Model model) {
        model.addAttribute("children", service.getAllChildren());
        return "admin/details";
    }

    @GetMapping("/admin/boys")
    public String adminBoys(Model model) {
        List<Child> children = service.getAllChildren();
        List<Child> boys = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getGender() == Child.Gender.male)
                boys.add(child);
        }

        model.addAttribute("children", boys);
        return "admin/boys";
    }

    @GetMapping("/admin/girls")
    public String adminGirls(Model model) {
        List<Child> children = service.getAllChildren();
        List<Child> girls = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getGender() == Child.Gender.female)
                girls.add(child);
        }

        model.addAttribute("children", girls);
        return "admin/girls";
    }

    @GetMapping("/admin/illness")
    public String adminIllness(Model model) {
        List<Child> children = service.getAllChildren();
        List<Child> illness = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getOriginalIllness() != null)
                illness.add(child);
        }

        model.addAttribute("children", illness);
        return "admin/illness";
    }

    @GetMapping("/admin/user_donations")
    public String adminDonationList(Model model,  @RequestParam int id) {
        model.addAttribute("donations", donationService.getAllDonations());
        User user = userService.getUserById(id);
        List<Donation> donations = new ArrayList<>();
        List<Donation> allDonations = donationService.getAllDonations();
        for (Donation donation : allDonations) {
            if (donation.getFirstName().equals(user.getFirstName()) && donation.getLastName().equals(user.getLastName())) {
                donations.add(donation);
            }
        }
        model.addAttribute("donations", donations);
        return "admin/user_donations";
    }

    @GetMapping("/admin/donation")
    public String adminDonation(Model model) {
        model.addAttribute("company",companyService.getCompanyById(1));
        return "admin/donation";
    }


    @GetMapping("/admin/contacts")
    public String contacts(Model model) {
        model.addAttribute("company",companyService.getCompanyById(1));
        return "admin/contacts";
    }

    @GetMapping("/admin/edit_contacts")
    public String editContacts(Model model) {
        model.addAttribute("company", companyService.getCompanyById(1));
        companyService.deleteCompany(1);
        return "admin/edit_contacts";
    }

    @PostMapping("/admin/edit_contacts")
    public String editPostContacts(@Valid Company company, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit_contacts";
        }
        company.setId(1);
        companyService.saveCompany(company);
        return "redirect:/admin/contacts";
    }

    @GetMapping("/admin/edit_manager")
    public String editManager(Model model, @RequestParam int id) {
        model.addAttribute("user", userService.getUserById(id));
        userService.deleteUser(id);
        return "admin/edit_manager";
    }

    @PostMapping("/admin/edit_manager")
    public String processEditManager(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/edit_manager";
        }
        user.setActive(true);
        user.setRoles("ROLE_MANAGER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin/managers";
    }

    @GetMapping("/admin/sponsors")
    public String sponsorsAdmin(Model model, @RequestParam int id) {
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
        return "admin/sponsors";
    }
}
