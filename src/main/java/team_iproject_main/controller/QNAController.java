package team_iproject_main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QNAController {

    @GetMapping("/Qna_question")
    public String Qnd_question () {
        return "Qna_question";
    }

}
