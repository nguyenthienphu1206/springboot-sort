package com.server.springbootsort.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/v1")
public class SortJsonController {

    @GetMapping
    public ResponseEntity<String> sendMessage() {

        return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }

    @PostMapping("/sort")
    public Map<String, Object> processJson(@RequestBody Map<String, Object> jsonList) {
        // Sử dụng TreeMap để lưu trữ key và value và sắp xếp key tự động.
        TreeMap<String, Object> sortedJsonMap = new TreeMap<>(jsonList);

        // Trả về TreeMap đã sắp xếp.
        return sortedJsonMap;
    }
}
