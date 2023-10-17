package com.example.eproject4.Controller.auth;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {
		return "admin_signup";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user")
										  UserRegistrationDto registrationDto,
									  	  Model model) {
		if (userService.existsByUsername(registrationDto.getUsername())) {
			model.addAttribute("error_admin", "The username already exists.");
			return "admin_signup";
		}

		userService.save(registrationDto);
		return "redirect:/login";
	}



}
