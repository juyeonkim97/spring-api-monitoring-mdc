package com.example.monitoring.controller;

import com.example.monitoring.model.RequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class TestController {

    @PostMapping("/post/test")
    public void post(@RequestBody RequestDTO requestDTO) {
        throw new NullPointerException();
    }

    @GetMapping("/get/test")
    public void get(RequestDTO requestDTO) {
        throw new IndexOutOfBoundsException();
    }
}
