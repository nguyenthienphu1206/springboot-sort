package com.server.springbootsort.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
@Slf4j
public class SortJsonController {

    @GetMapping
    public ResponseEntity<String> sendMessage() {

        return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }

    @PostMapping("/sort")
    public Map<String, Object> processJson(@RequestBody TreeMap<String, Object> jsonList) {
        // Sắp xếp TreeMap sử dụng Comparator
        TreeMap<String, Object> sortedJsonMap = new TreeMap<>(new JsonKeyComparator());

        // Thêm tất cả các phần tử từ jsonMap vào sortedJsonMap
        sortedJsonMap.putAll(jsonList);

        // Trả về TreeMap đã sắp xếp
        return sortedJsonMap;
    }

    private static class JsonKeyComparator implements Comparator<String> {
        @Override
        public int compare(String key1, String key2) {
            // Lấy tên biến bằng cách loại bỏ ký tự "@" ở đầu (nếu có)
            String variableName1 = key1.startsWith("@") ? key1.substring(1) : key1;
            String variableName2 = key2.startsWith("@") ? key2.substring(1) : key2;

            // So sánh các tên biến theo thứ tự bình thường
            int variableNameComparison = variableName1.compareTo(variableName2);

            // Nếu các tên biến giống nhau, thì so sánh key đầy đủ
            if (variableNameComparison == 0) {

                //log.info("value: " + key1.compareTo(key2));
                if (key1.compareTo(key2) > 0) {
                    return -1;
                }
                else {
                    return 1;
                }
            }

            // Nếu các tên biến khác nhau, trả về kết quả của so sánh tên biến
            return variableNameComparison;
        }
    }
}
