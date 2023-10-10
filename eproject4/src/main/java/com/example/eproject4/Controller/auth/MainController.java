package com.example.eproject4.Controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}

	@PostMapping("/login")
	public String loginUserAccount(Model model) {
		// Thêm thông báo lỗi vào model (nếu cần)
		model.addAttribute("error","Tài khoản hoặc mật khẩu không đúng.");
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
