package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.RecruitBoardDao;
import team_iproject_main.model.DO.*;
import team_iproject_main.model.Request.RecruitBoardEditRequest;
import team_iproject_main.model.Request.RequestKeyword;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitBoardDao recruitBoardDao;

    //주현 0512 수업시간중에 수정
    public void recruit_boardWrite(String email, RecruitBoardEditRequest req) throws Exception {
        //https://www.youtube.com/watch?v=sEenDC5fry4
        //https://youtu.be/sEenDC5fry4
        //링크가 두 종류임...
        String url = req.getOriginal_link();
        String returnurl = "";
        if(url.contains("https://www.youtube.com/watch?v=")){
            returnurl = "https://www.youtube.com/embed/" + req.getOriginal_link().substring(32);
        }
        else if(url.contains("https://youtu.be/")){
            returnurl = "https://www.youtube.com/embed/" + req.getOriginal_link().substring(17);
        }
        else {
            throw new Exception();
        }
        RecruitBoardDO recruitBoardDO = new RecruitBoardDO(req.getRecruit_title(), req.getDuty(), req.getWorkload(), req.getRequirement(),
                req.getSalary_detail(), req.getPreference(), req.getEdit_tools(), req.getEnvironment(), req.getWelfare(), req.getRecruit_detail(),
                req.getSalary(), LocalDate.parse(req.getDeadline()), returnurl, req.getChannel_categories());

        recruitBoardDO.setYoutuber_email(email);
        recruitBoardDao.createRecruitBoard(recruitBoardDO);

    }

    public boolean recruit_boardModify(RecruitBoardEditRequest req, int recruitNo, String email)  {
        String url = req.getOriginal_link();
        String returnurl = "";
        if(url.contains("https://www.youtube.com/watch?v=")){
            returnurl = "https://www.youtube.com/embed/" + req.getOriginal_link().substring(32);
        }
        else if(url.contains("https://youtu.be/")){
            returnurl = "https://www.youtube.com/embed/" + req.getOriginal_link().substring(17);
        }
        else if(url.contains("https://www.youtube.com/embed/")){
            returnurl = req.getOriginal_link();
        }
        else{
            return false;
        }


        RecruitBoardDO recruitBoardDO = new RecruitBoardDO(req.getRecruit_title(), req.getDuty(), req.getWorkload(), req.getRequirement(),
                req.getSalary_detail(), req.getPreference(), req.getEdit_tools(), req.getEnvironment(), req.getWelfare(), req.getRecruit_detail(),
                req.getSalary(), LocalDate.parse(req.getDeadline()), returnurl, req.getChannel_categories());

        recruitBoardDao.modifyRecruitBoard(recruitBoardDO, recruitNo, email);
        return true;
    }

    //주현
    public List<RecruitDO> findRecruit(String email) {
        return recruitBoardDao.findAllApplyByEmail(email);
    }

    //희수
    //구인글 게시글 상세 조회
    public RecruitDO boardview(int recruitNo){
        return recruitBoardDao.selectRecruitPost(recruitNo);
    }


    public List<ChannelCategoryDO> getChannelCategory(int recruitNo){
        return recruitBoardDao.getCategories(recruitNo);
    }

    public List<EditToolsRecruitDO> getEditTools(int recruitNo){
        return recruitBoardDao.getTools(recruitNo);
    }

    //희수
    //구인글 게시글 조회
    // 준영 페이징 추가
    public List<RecruitSearchDO> findRecruit(int page, int postsPerPage) {
        int offset = (page - 1) * postsPerPage;
        return recruitBoardDao.findRecruit(postsPerPage, offset);
    }


    // 준영 페이징 추가
    public int getTotalPosts() {
        return recruitBoardDao.getTotalPosts();
    }

    public int getSearchTotalPosts(RequestKeyword keyword) {
        return recruitBoardDao.getSearchTotalPosts(keyword);
    }

    public void deleteRecruit(int recruitNo){
        recruitBoardDao.deleteRecruitPost(recruitNo);
    }

    // 겸손 검색
    public List<RecruitSearchDO> Recruit_finder(RequestKeyword keyword, int page, int postsPerPage) {

        int offset = (page - 1) * postsPerPage;
        List<RecruitSearchDO> recruitDO = recruitBoardDao.SearchFinder(keyword, postsPerPage, offset);

        return recruitDO;

    }

    // 0512 준원
    // 유튜버 -> 작성한 구인글 -> 지원자 확인
    public List<MyRecruitDO> myRecruitList(String youtuber_email) {
        return recruitBoardDao.findMyRecruit(youtuber_email);
    }

}
