package com.example.eproject4.Controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

	@GetMapping("/login")
	public String showLoginForm(Model model) {
//		model.addAttribute("error","Sai tài khoản hoặc mật khẩu.");
		return "login";
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
