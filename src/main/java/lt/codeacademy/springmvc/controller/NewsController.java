package lt.codeacademy.springmvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/news")
public class NewsController {

    @GetMapping
    public String newsPage(){
        return "news";
    }
}
