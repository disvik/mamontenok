package com.example.demo.user;

import com.example.demo.admin.CompanyService;
import com.example.demo.admin.Donation;
import com.example.demo.admin.DonationService;
import com.example.demo.child.Child;
import com.example.demo.child.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private ChildService childService;
    private FavouriteService favouriteService;
    private DonationService donationService;
    private MyUserDetailsService userDetailsService;
    private CompanyService companyService;

    @Autowired
    public void setCompanyServiceService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserDetailsService(MyUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setDonationService(DonationService donationService) {
        this.donationService = donationService;
    }

    @Autowired
    public void setChildService(ChildService service) {
        this.childService = service;
    }

    @Autowired
    public void setUserService(UserService service) {
        this.userService = service;
    }

    @Autowired
    public void setFavouriteService(FavouriteService service) {
        this.favouriteService = service;
    }

  @GetMapping("/registration_form")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "registration_form";
    }

    @GetMapping("/sign_in")
    public String signIn(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        return "logout";
    }

    @GetMapping("/users_list")
    public String usersList(Model model) {
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
        return "users_list";
    }

    @GetMapping("/user/users_list")
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
        return "user/users_list";
    }

    @GetMapping("/user/index")
    public String userIndex(Model model, Principal principal) {
        final UserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("children", childService.getAllChildren());
        if (user.getAuthorities().toString().contains("ROLE_ADMIN"))
            return "admin/index";
        else
            if (user.getAuthorities().toString().contains("ROLE_MANAGER"))
            return "admin/index";
        else
            return "user/index";
    }

    @GetMapping("/user/child_list")
    public String userChildList(Model model) {
        model.addAttribute("children", childService.getAllChildren());
        return "user/child_list";
    }

    @GetMapping("/user/about_project")
    public String userAboutProject() {
        return "user/about_project";
    }


    @GetMapping("/user/my_account")
    public String myAccount(Model model, Principal principal) {
        final MyUserDetails user = userDetailsService.loadUserByUsername(principal.getName());
        long id = user.getUser_id();
        model.addAttribute("user", userService.getUserById(id));
        return "user/my_account";
    }

    @PostMapping("/registration_form")
    public String processRegistration(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration_form";
        }
        user.setActive(true);
        user.setRoles("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/user/child")
    public String processAddStudentForm(Model model, @RequestParam int id) {
        model.addAttribute("child", childService.getChildById(id));
        return "user/child";
    }
    @PostMapping("/user/my_account")
    public String myAccount(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/my_account";
        }
        userService.saveUser(user);
        return "user/my_account";
    }

    @PostMapping("/user/child")
    public String processAddStudentForm(Model model, @RequestParam long id, Principal principal) {
        Child child = childService.getChildById(id);
        Favourite favourite = new Favourite(child.getId(), child.getFirstName(), child.getYearOfBirth(),
                child.getGender(), child.getCode(), child.getHobby(), child.getPhotoName(), child.getIllness());
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        long user_id = userDetails.getUser_id();
        User user = userService.getUserById(user_id);
        favouriteService.addFavourite(favourite, user_id);
        userService.saveUser(user);
        model.addAttribute("favourites", user.getFavourites());
        return "user/favourites";
    }

    @GetMapping("/user/favourites")
    public String userFavourites(Model model, Principal principal) {
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        long user_id = userDetails.getUser_id();
        User user = userService.getUserById(user_id);
        model.addAttribute("favourites", user.getFavourites());
        return "user/favourites";
    }


    @GetMapping("/user/donations_list")
    public String adminDonationList(Model model) {
        model.addAttribute("donations", donationService.getAllDonations());
        return "user/donations_list";
    }

    @GetMapping("/user/details")
    public String userChildDetails(Model model) {
        model.addAttribute("children", childService.getAllChildren());
        return "user/details";
    }

    @GetMapping("/user/boys")
    public String userBoys(Model model) {
        List<Child> children = childService.getAllChildren();
        List<Child> boys = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getGender() == Child.Gender.male)
                boys.add(child);
        }

        model.addAttribute("children", boys);
        return "user/boys";
    }

    @GetMapping("/user/girls")
    public String userGirls(Model model) {
        List<Child> children = childService.getAllChildren();
        List<Child> girls = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getGender() == Child.Gender.female)
                girls.add(child);
        }

        model.addAttribute("children", girls);
        return "user/girls";
    }

    @GetMapping("/user/illness")
    public String userIllness(Model model) {
        List<Child> children = childService.getAllChildren();
        List<Child> illness = new ArrayList<>();
        for (Child child : children)
        {
            if (child.getOriginalIllness() != null)
                illness.add(child);
        }

        model.addAttribute("children", illness);
        return "user/illness";
    }

    @GetMapping("/user/user_donations")
    public String userDonationList(Model model,  Principal principal) {
        final MyUserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        long user_id = userDetails.getUser_id();
        User user = userService.getUserById(user_id);
        List<Donation> donations = new ArrayList<>();
        List<Donation> allDonations = donationService.getAllDonations();
        for (Donation donation : allDonations)
        {
            if (donation.getFirstName().equals(user.getFirstName()) && donation.getLastName().equals(user.getLastName()))
            {
                donations.add(donation);
            }

        }
        model.addAttribute("donations", donations);
        return "user/user_donations";
    }

    @GetMapping("/user/donation")
    public String userDonation(Model model) {
        model.addAttribute("company",companyService.getCompanyById(1));
        return "user/donation";
    }

    @GetMapping("/user/contacts")
    public String contacts(Model model) {
        model.addAttribute("company",companyService.getCompanyById(1));
        return "user/contacts";
    }

    @GetMapping("/user/sponsors")
    public String sponsorsUser(Model model, @RequestParam int id) {
        List<Donation> donations = donationService.getAllDonations();
        List<Donation> sponsors = new ArrayList<>();
        for (Donation donation : donations)
        {
            if (donation.getCode().equals(childService.getChildById(id).getCode()))
            {
                sponsors.add(donation);

            }
        }
        model.addAttribute("users", sponsors);
        return "user/sponsors";
    }

}