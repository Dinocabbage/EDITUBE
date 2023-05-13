package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.Request.PortfolioEditRequest;
import team_iproject_main.model.SO.PortfolioService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    //희수
    //구직자 게시판 조회
    //준영 페이징 추가
    @GetMapping("/portfolio_board")
    public String list2(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        int postsPerPage = 10;
        int pageNavigationLinks = 5;

        List<PortfolioDO> portfolioDO = portfolioService.findPortfolio(page, postsPerPage);
        model.addAttribute("portfolioDO", portfolioDO);

        int totalPosts = portfolioService.getTotalPosts();
        int totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);
        int startPage = Math.max(1, page - (pageNavigationLinks / 2));
        int endPage = Math.min(startPage + pageNavigationLinks - 1, totalPages);

        model.addAttribute("currentPage", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "portfolio_board";
    }

    @GetMapping("/myPage/portfolio_result")
    public String portfolioresult(Model model, HttpSession session) {
        try {
            PortfolioDO portfolioDO = portfolioService.portfoloview(String.valueOf(session.getAttribute("email")));
            model.addAttribute("portfolioDO",portfolioDO);
        }
        catch(EmptyResultDataAccessException e) {
            return "portfolio_edit";
        }

        return "portfolio_result";
    }

    //5.11 양서림
    @PostMapping("/portfolio_edit")
    public String portfolioedit(HttpSession session, PortfolioEditRequest edit, Model model, @RequestParam("edit_link") String[] edit_link) {
        String email = String.valueOf(session.getAttribute("email"));
        portfolioService.portfolioupdate(edit,email); //편집영상제외 db저장

        portfolioService.portfolioVideoUpdate(edit_link,email); //편집영상 db 저장
        model.addAttribute("msg","포트폴리오가 수정되었습니다");
        return "redirect:/portfolio_edit";
    }
    @GetMapping("/portfolio_edit")
    public String portfolioForm(String email, Model model,HttpSession session , RedirectAttributes redirectAttributes) {

        if(!email.equals(String.valueOf(session.getAttribute("email")))) {
            redirectAttributes.addFlashAttribute("msg","작성자만 수정 할 수 있습니다.");
            return "redirect:/portfolio_result?email=" + email;
        }
        try {
        PortfolioDO portfolioDO = portfolioService.portfoloview(email);
        redirectAttributes.addFlashAttribute("portfolioDO", portfolioDO);
        }
        catch(EmptyResultDataAccessException e) {

        }

        return "portfolio_edit";
    }

    //희수
    //구직자 상세 조회
    @GetMapping("/portfolio_result")
    public String portfolioresult(String email, Model model) {
        PortfolioDO portfolioDO = portfolioService.portfoloview(email);
        model.addAttribute("portfolioDO", portfolioDO);
        return "portfolio_result";
    }
    //희수
    //포트폴리오 삭제
    @GetMapping("/portfolio_delete")
    public String portfolio_delete(String email1, HttpSession session, RedirectAttributes redirectAttributes, Model model) {

        if(!email1.equals(String.valueOf(session.getAttribute("email")))){
            redirectAttributes.addFlashAttribute("msg","작성자만 삭제 할 수 있습니다.");
            return "redirect:/portfolio_result?email=" + email1;
        }
        portfolioService.deletePortfole(email1);
        return list2(1, model);
    }

    // 겸손
    @PostMapping("/portfolio_board/search")
    public String portfolio_board_search(
            @RequestParam("folio_search_text") String folio_search_text,
            @RequestParam("location") String location,
            @RequestParam(value = "edit_tools_folio", required = false) String[] edit_tools_folio,
            Model model) {
        if((edit_tools_folio == null || edit_tools_folio.length == 0)&& folio_search_text.equals("") && location.equals("")) {
            return list2(1, model);
        }

        if(edit_tools_folio == null) {
            edit_tools_folio = new String[]{};
        }

        List<PortfolioDO> portfolioDO = portfolioService.PoforFinder(folio_search_text, location, edit_tools_folio);
        model.addAttribute("portfolioDO", portfolioDO);

        return "Portfolio_finder";
    }
}
