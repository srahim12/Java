package com.rahim.Web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String getIndex(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model){
        String hi = "kartakarta";
        model.addAttribute("name",hi);
        return "index";
    }
}


