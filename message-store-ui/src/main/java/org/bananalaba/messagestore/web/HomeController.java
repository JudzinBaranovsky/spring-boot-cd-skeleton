package org.bananalaba.messagestore.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class HomeController {

    @Value("${messageApi.host}")
    private final String messageApiHost;

    @GetMapping("/home")
    public String home(Model model, @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) {
        model.addAttribute(
            "authToken",
            client.getAccessToken().getTokenValue()
        );
        model.addAttribute(
            "messageApiHost",
            messageApiHost
        );

        return "index";
    }

}
