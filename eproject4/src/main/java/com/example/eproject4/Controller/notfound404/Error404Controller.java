package com.example.eproject4.Controller.notfound404;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Error404Controller implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String handleError() {
        return "error_notfound"; // Trả về tên của trang HTML báo lỗi tùy chỉnh
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }
}