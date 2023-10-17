package com.example.eproject4.Controller.user;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterUserController {
    private UserService userService;

    public RegisterUserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "customer_register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user")
                                      UserRegistrationDto registrationDto,
                                      Model model) {
        if (userService.existsByUsername(registrationDto.getUsername())) {
            model.addAttribute("error_user", "The username already exists.");
            return "customer_register";
        }

        userService.save(registrationDto);
        return "redirect:/login";
    }


}
