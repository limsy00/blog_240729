package com.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
  @ResponseBody
    @RequestMapping("/test1")
    public String helloWorld() {
        return "Hello world!";
    }
}