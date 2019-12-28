package com.carter.book.web;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/profile")
@RestController
public class ProfileController {

    private final Environment env;

    @GetMapping
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        System.out.println(profiles);
        List<String> prodProfiles = Arrays.asList("prod", "prod1", "prod2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
            .filter(prodProfiles::contains)
            .findAny()
            .orElse(defaultProfile);
    }
}
