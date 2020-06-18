package springutils.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

public class ValidorLogin {

    @RequestMapping(value = {"", "/", "/login"})
    public String login(/*Model model, */@AuthenticationPrincipal User user) {
        return Optional.ofNullable(user).isPresent() ? "redirect:/app" : "login/login";
    }

//	SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getPrincipal() : null;

}
