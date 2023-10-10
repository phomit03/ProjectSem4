package com.example.eproject4.Controller.auth;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
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
		return "signup";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, Model model) {
		if (userService.existsByUsername(registrationDto.getUsername())) {
			model.addAttribute("error", "Tên người dùng đã tồn tại.");
			return "signup";
		}

		userService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
