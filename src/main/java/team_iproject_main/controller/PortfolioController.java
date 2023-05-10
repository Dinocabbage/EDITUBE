package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.SO.PortfolioService;

import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    //희수
    //구직자 게시판 조회
    @GetMapping("/portfolio_board")
    public String list2(Model model) {
        List<PortfolioDO> portfolioDO = portfolioService.findPortfolio();
        model.addAttribute("portfolioDO", portfolioDO);
        return "portfolio_board";
    }
    //희수
    //구직자 상세 조회
    @GetMapping("/portfolio_result")
    public String portfolioresult(String title, Model model) {
        PortfolioDO portfolioDO = portfolioService.portfoloview(title);
        model.addAttribute("portfolioDO",portfolioDO);
        return "portfolio_result";
    }

}
