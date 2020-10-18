package com.petrov.Boot.controller;

import com.petrov.Boot.persist.entity.Product;
import com.petrov.Boot.persist.entity.User;
import com.petrov.Boot.persist.repo.ProductRepository;
import com.petrov.Boot.persist.repo.ProductSpecification;
import com.petrov.Boot.persist.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String allUsers(Model model) {

        List<User> allUsers = userRepository.findAll();

        model.addAttribute("users", allUsers);
        return "users";
    }

}
