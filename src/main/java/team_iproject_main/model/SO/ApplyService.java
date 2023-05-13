package team_iproject_main.model.SO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_iproject_main.model.DAO.ApplyEditorDao;
import team_iproject_main.model.DO.ApplierListDO;
import team_iproject_main.model.DO.ApplyEditorDO;

import java.util.List;

@Service
public class ApplyService {

    @Autowired
    private ApplyEditorDao applyEditorDao;

    public void applyUploadVideo(int recruitNo, String editor_email, String edited_link, String editor_memo) throws Exception {
        //https://www.youtube.com/watch?v=sEenDC5fry4
        //https://youtu.be/sEenDC5fry4
        //링크가 두 종류임...
        String returnurl = "";
        if(edited_link.contains("https://www.youtube.com/watch?v=")){
            returnurl = "https://www.youtube.com/embed/" + edited_link.substring(32);
        }
        else if(edited_link.contains("https://youtu.be/")){
            returnurl = "https://www.youtube.com/embed/" + edited_link.substring(17);
        }
        else {
            throw new Exception();
        }
        applyEditorDao.updateApplyEditor(recruitNo, editor_email, edited_link, editor_memo);
    }

    public ApplyEditorDO getLinkAndMemo(int recruitNo, String email){
        return applyEditorDao.findApplyEditorByNumAndEmail(recruitNo, email);
    }

    //0512 주현 수업시간중 추가
    public void deleteApplyEditorByNumEmail(int recruitNo, String email){
        applyEditorDao.deleteApplyEditor(recruitNo, email);
    }

    //주현
    //지원자 넣기
    public void recruitBoardApply(int recruitNo, String email){
        applyEditorDao.addApplyEditor(recruitNo, email);
    }

    public ApplyEditorDO findApplyEditorByNumEmail(int recruitNo, String email){
        return applyEditorDao.findApplyEditorByNumAndEmail(recruitNo, email);
    }

    // 0512 준원
    // 마이페이지 -> 작성한 구인글 -> 지원자 확인
    // 준영 페이징 추가
    public List<ApplierListDO> myApplierList(int recruitNo, int page, int postsPerPage) {
        int offset = (page - 1) * postsPerPage;
        return applyEditorDao.myRecruitApplierList(recruitNo, postsPerPage, offset);
    }

    public int getTotalApplier(int recruitNo) {
        return applyEditorDao.getTotalApplier(recruitNo);
    }
}
