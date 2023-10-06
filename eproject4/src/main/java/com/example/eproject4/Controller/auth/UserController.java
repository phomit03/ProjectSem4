package com.example.eproject4.Controller.auth;

import com.example.eproject4.Entity.User;
import com.example.eproject4.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("")
	public String getAllUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user_list";
	}

//	@GetMapping("/{id}")
//	public String getUserById(@PathVariable Long id, Model model) {
//		User user = userService.getUserById(id);
//		model.addAttribute("user", user);
//		return "user-details";
//	}

//	@GetMapping("/new")
//	public String showUserForm(Model model) {
//		model.addAttribute("user", new User());
//		return "user_list";
//	}

//	@PostMapping("/new")
//	public String createUser(@ModelAttribute("user") User user) {
//		userService.createUser(user);
//		return "redirect:/user/";
//	}

	@GetMapping("/{id}/edit")
	public String showEditUserForm(@PathVariable Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "user_update";
	}

	@PostMapping("/{id}/edit")
	public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
		userService.updateUser(id, user);
		return "redirect:/user";
	}

	@GetMapping("/{id}/delete")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/user";
	}

}
