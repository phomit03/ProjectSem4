package com.example.eproject4.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeamController {
    @RequestMapping("/admin/team")
    public String team() {
        return "admin_team";
    }
}
