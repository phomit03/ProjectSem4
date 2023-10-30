package com.example.eproject4.Controller.admin;

import com.example.eproject4.Entity.New;
import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.UserRepository;
import com.example.eproject4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	private UserService userService;
	private UserRepository userRepository;
	@Autowired
	public UserController(UserService userService,UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
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
	public ResponseEntity<String> softDelete(@PathVariable Long id) {
		try {
			userService.softDelete(id);
			return ResponseEntity.ok("Delete user successfully.");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error!");
		}
	}

	//phan trang
	@GetMapping()
	public String getAllNews(Model model,
							 @RequestParam(name = "username", required = false) String username,
							 @RequestParam(name = "phone", required = false) String phone,
							 @RequestParam(name = "email", required = false) String email,
							 @RequestParam(name = "status", required = false) Integer status
	) {
		model.addAttribute("title", "Users");
		return findPaginated(1, model, username, phone,email,status);
	}

	@GetMapping("/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
								Model model,
								@RequestParam(name = "username", required = false) String username,
								@RequestParam(name = "phone", required = false) String phone,
								@RequestParam(name = "email", required = false) String email,
								@RequestParam(name = "status", required = false) Integer status
	) {
		int pageSize = 6;

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<User> result = userRepository.searchUsers(username, status,phone,email, pageable);
		Page<User> page = new PageImpl<>(result, pageable,userRepository.searchUsers1(username,status,phone,email).size());
		List<User> users = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("users", users);
		model.addAttribute("username", username);
		model.addAttribute("status", status);
		model.addAttribute("phone", phone);
		model.addAttribute("email", email);
		return "admin_user_list";
	}

}