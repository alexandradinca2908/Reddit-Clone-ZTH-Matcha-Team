package org.matcha.springbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class MenuController {
    @GetMapping
    public String hello() {
        return "🍵Hello from the Matcha team!";
    }

    @GetMapping("filters")
    public Map<String, Integer> getFilters() {
        List<String> filterNames = List.of("DoNothing", "GrayScale", "Sepia", "Inverted");
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < filterNames.size(); i++) {
            map.put(filterNames.get(i), i);
        }
        return map;
    }
}
