package com.example.eproject4.Controller.auth;

import com.example.eproject4.Entity.Employee;
import com.example.eproject4.Entity.Role;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	private UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}
	//	@GetMapping("")
//	public String getAllUsers(Model model) {
//		List<User> users = userService.getAllUsers();
//		model.addAttribute("users", users);
//		return "user_list";
//	}
	@GetMapping
	public String getAllUsers(Model model) {
		return findPaginated(1, model);
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
		return "admin_user_update";
	}

	@PostMapping("/{id}/edit")
	public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
		userService.updateUser(id, user);
		return "redirect:/admin/user";
	}

	@GetMapping("/{id}/delete")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/admin/user";
	}

	// phan trang
	@GetMapping("/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
								Model model) {
		int pageSize = 5;


		Page<User> page = userService.findPaginated(pageNo, pageSize);
		List<User> users = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("users", users);
		return "admin_user_list";
	}

}