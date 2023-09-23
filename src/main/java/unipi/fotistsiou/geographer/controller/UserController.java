package unipi.fotistsiou.geographer.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import unipi.fotistsiou.geographer.entity.User;
import unipi.fotistsiou.geographer.service.UserService;
import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome(
            Model model,
            Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = userService.findOneByEmail(authUsername);
        if (optionalUser.isPresent()) {
            String username = optionalUser.get().getFirstName();
            Long userId = optionalUser.get().getId();
            model.addAttribute("username", username);
            model.addAttribute("userId", userId);
        }
        return "home";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/register/student")
    public String getRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register_student";
    }

    @PostMapping("/register/{role}")
    public String registerUser(
            @PathVariable String role,
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model
    ){
        Optional<User> optionalUser = userService.findOneByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            result.rejectValue("email", "error.email", "Υπάρχει ήδη ένας λογαριασμός εγγεγραμμένος με αυτό το email. Δοκιμάστε με άλλο λογαριασμό email.");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            if (role.equals("ROLE_STUDENT")) {
                return "register_student";
            }
            return "404";
        }
        userService.saveUser(user, role);
        return "redirect:/login?success_register";
    }

    @GetMapping("/account/info/{id}")
    @PreAuthorize("isAuthenticated()")
    public String getAccountInfo(
            @PathVariable Long id,
            Model model,
            Principal principal
    ){
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }
        Optional<User> optionalUser = this.userService.getUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getEmail().equals(authUsername)) {
                return "404";
            }
            model.addAttribute("user", user);
            return "account_info";
        } else {
            return "404";
        }
    }
}