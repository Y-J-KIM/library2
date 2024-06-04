package com.mysite.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuideController {

    @GetMapping("/guide/time")
    public String showGuideTime() {
        return "guide_time";
    }

    @GetMapping("/guide/data")
    public String showGuideData() {
        return "guide_data";
    }
}
