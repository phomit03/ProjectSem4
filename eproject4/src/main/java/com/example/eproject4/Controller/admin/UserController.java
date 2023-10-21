package com.example.eproject4.Controller.admin;

import com.example.eproject4.Entity.Employee;
import com.example.eproject4.Entity.Role;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	private UserService userService;
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String getAllUsers(Model model) {
		model.addAttribute("title", "Users");

		return findPaginated(1, model);
	}


	@GetMapping("/{id}/edit")
	public String showEditUserForm(@PathVariable Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "admin_user_update";
	}

	@PostMapping("/{id}/edit")
	public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		try {
			userService.updateUser(id, user);
			redirectAttributes.addFlashAttribute("success", "Update successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Failed to update!");
		}
		return "redirect:/admin/user";
	}

	@GetMapping("/{id}/delete")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("Delete user successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
		}
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