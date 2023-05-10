package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import team_iproject_main.model.DO.RecruitDO;
import team_iproject_main.model.Request.RecruitBoardEditRequest;
import team_iproject_main.model.SO.RecruitService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @GetMapping("/recruit_board")
    public String list1(Model model) {
        List<RecruitDO> recruitDO = recruitService.findRecruit();
        model.addAttribute("recruitDO", recruitDO);
        return "recruit_board";
    }

    @GetMapping("/recruit_result")
    public String recruitresult(int recruitNo, Model model) {
        RecruitDO recruitDO = recruitService.boardview(recruitNo);
        model.addAttribute("recruitDO",recruitDO);
        return "recruit_result";
    }

    @GetMapping("/recruit_board_edit")
    public String recruit_board_editForm() {
        return "recruit_board_edit";
    }

    @PostMapping("/recruit_board_edit")
    public String recruit_board_edit(RecruitBoardEditRequest req, HttpSession session, Model model) {
        String email = session.getAttribute("email").toString();
        recruitService.recruit_boardWrite(email, req);
        model.addAttribute("msg", "구인글이 등록되었습니다.");

        return "redirect:/recruit_board";
    }

}
