package com.ssafy.project.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ssafy.project.model.dto.User;
import com.ssafy.project.util.CustomUserDetails;

@ControllerAdvice
public class GlobalLoginUserAdvice {

    @ModelAttribute("loginUser")
    public User loginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }

        return null;
    }
}