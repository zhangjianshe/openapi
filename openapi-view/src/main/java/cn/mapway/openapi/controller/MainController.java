package cn.mapway.openapi.controller;


import org.nutz.lang.Files;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;

@Controller
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class MainController {


    /**
     * Main Index For Test OPENAPI-SPECIFICATION VIEWR
     * @return
     */
    @RequestMapping({"/"})
    public String index()
    {
        return "index";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public String data() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:static/data.json");
        return Files.read(file);
    }
}
