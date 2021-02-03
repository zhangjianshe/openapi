package cn.mapway.openapi.controller;


import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import org.nutz.lang.Files;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;


@Controller
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class MainController {


    /**
     * Main Index For Test OPENAPI-SPECIFICATION VIEWR
     *
     * @return
     */
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public String data() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:static/data.json");
        return Files.read(file);
    }
}
