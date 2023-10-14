package com.example.eproject4.Controller.auth;

import com.example.eproject4.DTO.UserRegistrationDto;
import com.example.eproject4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class MainController {
	private UserService userService;
	@Autowired
	public MainController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "admin_login";
	}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/index1")
	public String home1() {
		return "index1";
	}
}
