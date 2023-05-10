package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.PortfolioDao;
import team_iproject_main.model.DO.PortfolioDO;

import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioDao portfolioDao;

    //희수
    //포트폴리오 게시글 조회
    public List<PortfolioDO> findPortfolio() { return portfolioDao.PortfolioAll(); }

    //희수
    //포트폴리오 게시글 상세 조회
    public PortfolioDO portfoloview(String title){
        return portfolioDao.selectPortfolioPost(title);
    }

}


