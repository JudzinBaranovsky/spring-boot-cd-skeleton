package org.bananalaba.messagestore.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class HomeController {

    @Value("${message.api.host}")
    private String messageApiHost;

    @GetMapping("/home")
    public String home(Model model, @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) {
        model.addAttribute("authToken", "<not implemented yet>");
        model.addAttribute("messageApiHost", messageApiHost);

        return "index";
    }

}
