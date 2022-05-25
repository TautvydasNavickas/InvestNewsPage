package lt.codeacademy.springmvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/contact")
public class ContactController {


    @GetMapping
    public String contactPge(){
        return "contact";
    }
}
