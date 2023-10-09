package com.example.eproject4.sesstion;

import com.example.eproject4.Entity.User;
import com.example.eproject4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Autowired
    public CustomAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
      /*  // Truy cập thông tin người dùng đã đăng nhập
        String username = authentication.getName();

        // Lưu thông tin người dùng vào session
        request.getSession().setAttribute("loggedInUser", username);*/





        User user =  userRepository.findByUsername(authentication.getName());

        // Lưu user model vào session
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);


        // Chuyển hướng đến trang chính sau khi đăng nhập thành công
        response.sendRedirect("/index1");
    }
}
