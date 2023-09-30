package com.server.springbootsort.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
@Slf4j
public class SortJsonController {

    @GetMapping
    public ResponseEntity<String> sendMessage() {

        return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }

    @PostMapping("/checkFormat")
    public List<String> checkFormat(@RequestBody TreeMap<String, Object> jsonList) {

        Set<String> keySet = jsonList.keySet();

        List<String> notFormattedKey = new ArrayList<>();

        for (String key : keySet) {

            Pattern pattern = Pattern.compile("[A-Z]");
            Matcher matcher = pattern.matcher(key);

            if (matcher.find()) {
                notFormattedKey.add(key);
            }

        }

        return notFormattedKey;
    }

    @PostMapping("/sort")
    public Map<String, Object> processJson(@RequestBody TreeMap<String, Object> jsonList) {

        // Tạo json theo format
        //log.info("" + jsonList);
        //jsonList = createFormattedJson(jsonList);

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
                // sắp xếp theo kí tự @(tên biến) luôn nằm kề sau tên biến
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

    private TreeMap<String, Object> createFormattedJson(TreeMap<String, Object> jsonList) {

        TreeMap<String, Object> formattedJson = new TreeMap<>();
        Set<String> keySet = jsonList.keySet();

        for (String key : keySet) {

            // Sử dụng hàm split với biểu thức chính quy để tách chuỗi
            String[] parts = key.split("(?=[A-Z])");

            Object value = jsonList.get(key);

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i].toLowerCase(); // Chuyển thành chữ thường
                result.append(part);
                if (i < parts.length - 1) {
                    result.append("_"); // Thêm dấu gạch dưới (_) nếu không phải phần tử cuối cùng
                }
            }

            formattedJson.put(result.toString(), value);
        }

        return formattedJson;
    }
}
