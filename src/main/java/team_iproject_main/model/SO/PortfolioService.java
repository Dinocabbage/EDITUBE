package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.PortfolioDao;
import team_iproject_main.model.DO.PortfolioDO;
import team_iproject_main.model.DO.PortfolioEditDO;
import team_iproject_main.model.Request.PortfolioEditRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioDao portfolioDao;

    //희수
    //포트폴리오 게시글 조회
    //준영 페이징 추가
    public List<PortfolioDO> findPortfolio(int page, int postsPerPage) {
        int offset = (page - 1) * postsPerPage;
        return portfolioDao.PortfolioAll(postsPerPage, offset);
    }

    //준영 페이징 추가
    public int getTotalPosts() { return portfolioDao.getTotalPosts(); }

    //희수
    //포트폴리오 게시글 상세 조회
    public PortfolioDO portfoloview(String email){
        return portfolioDao.selectPortfolioPost(email);
    }

    public void portfolioupdate(PortfolioEditRequest edit, String email){
        PortfolioEditDO portfolioEditDO = new PortfolioEditDO(edit.getLocation(),edit.getEdit_tools()
                , LocalDate.parse(edit.getStartdate()),LocalDate.parse(edit.getEnddate()),edit.getCareer()
                ,edit.getMessage(),edit.getMain_link(),edit.getTitle(),edit.getSalary(),edit.getWorktype(),edit.getToyoutuber());

        portfolioEditDO.setEdit_email(email);
        portfolioDao.deletevideo(email);
        portfolioDao.portfolioupdate(portfolioEditDO);
    }

    public void portfolioVideoUpdate(String[] edit_link, String email){
        int count=1;
        for (String link : edit_link) {
            if (!link.isEmpty()) {
                count++;
                // color 값이 비어있지 않은 경우 DB에 저장하는 로직을 작성
                portfolioDao.editlinkUpdate(link,email,count);
            }
        }

    }

    //희수
    //포트폴리오 삭제
    public void deletePortfole(String email1){
        portfolioDao.deletePortfolioPost(email1);
    }


    // 겸손
    public List<PortfolioDO> PoforFinder(String folio_search_text, String location, String[] edit_tools_folio) {

        return portfolioDao.FolioFinder(folio_search_text, location, edit_tools_folio);
    }

}


