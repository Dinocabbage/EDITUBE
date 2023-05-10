package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.RecruitBoardDao;
import team_iproject_main.model.DO.RecruitBoardDO;
import team_iproject_main.model.DO.RecruitDO;
import team_iproject_main.model.Request.RecruitBoardEditRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitBoardDao recruitBoardDao;

    public void recruit_boardWrite(String email, RecruitBoardEditRequest req){
        RecruitBoardDO recruitBoardDO = new RecruitBoardDO(req.getRecruit_title(), req.getDuty(), req.getWorkload(), req.getRequirement(),
                req.getSalary_detail(), req.getPreference(), req.getEdit_tools(), req.getEnvironment(), req.getWelfare(), req.getRecruit_detail(),
                req.getSalary(), LocalDate.parse(req.getDeadline()), req.getOriginal_link(), req.getChannel_categories());

        recruitBoardDO.setYoutuber_email(email);
        recruitBoardDao.createRecruitBoard(recruitBoardDO);

    }

    //희수
    //구인글 게시글 조회
    public List<RecruitDO> findRecruit() {
        return recruitBoardDao.RecruitAll();
    }

    //희수
    //구인글 게시글 상세 조회
    public RecruitDO boardview(int recruitNo){
        return recruitBoardDao.selectRecruitPost(recruitNo);
    }


}
