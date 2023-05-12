package team_iproject_main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team_iproject_main.model.DO.*;
import team_iproject_main.model.SO.ApplyService;
import team_iproject_main.model.SO.RecruitService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ApplyController {

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private ApplyService applyService;


    @GetMapping("/apply")
    public String applyAndGoMyPage(HttpSession session, int recruitNo, RedirectAttributes re){
        String email = String.valueOf(session.getAttribute("email"));
        ApplyEditorDO applyEditorDO = applyService.findApplyEditorByNumEmail(recruitNo, email);
        if(applyEditorDO != null) {
            re.addAttribute("msg", "이미 지원한 구인글입니다.");
        }
        else{
            applyService.recruitBoardApply(recruitNo, email);
            re.addAttribute("msg", "지원 완료 되었습니다\n마이페이지 지원현황에서 확인하세요!");
        }
        return "redirect:/applynow";
    }

    //0512주현 수업시간 중 수정
    @GetMapping("/applydelete")
    public String applyDelete(HttpSession session, int recruitNo, RedirectAttributes re){
        String email = session.getAttribute("email").toString();
        applyService.deleteApplyEditorByNumEmail(recruitNo, email);
        re.addAttribute("msg","지원 취소되었습니다.");
        return "redirect:/applynow";
    }

    @GetMapping("applynow")
    public String applynowForm(Model model, HttpSession session){
        String email = session.getAttribute("email").toString();
        List<RecruitDO> recruitDO = recruitService.findRecruit(email);

        for(RecruitDO redo : recruitDO){
            if(redo.getDeadline().compareTo(LocalDate.now()) < 0){
                redo.setOverdate(false);
            }else{
                redo.setOverdate(true);
            }
        }
        model.addAttribute("recruitDO", recruitDO);
        return "applynow";
    }

    //주현 0512
    //주현 0512 수업시간 중에 수정
    @GetMapping("/applynow_upload")
    public String applynow_uploadForm(int recruitNo, Model model, HttpSession session){
        RecruitDO recruitDO = recruitService.boardview(recruitNo);
        String email = session.getAttribute("email").toString();
        List<ChannelCategoryDO> chCategories = recruitService.getChannelCategory(recruitNo);
        List<EditToolsRecruitDO> editTools = recruitService.getEditTools(recruitNo);
        String categories = " ";
        String tools = " ";
        //타임리프에서 th:each 사용시 자동으로 줄바꿈 되게 되어 있어서 이렇게 했습니다. 쩔수 없임...
        for(ChannelCategoryDO category : chCategories){
            categories += category.getCategory() + " ";
        }
        for(EditToolsRecruitDO tool : editTools){
            tools += tool.getEdit_tool() + " ";
        }

        ApplyEditorDO applyEditorDO = applyService.getLinkAndMemo(recruitNo, email);

        model.addAttribute("recruitDO",recruitDO);
        model.addAttribute("categories", categories);
        model.addAttribute("tools", tools);
        model.addAttribute("applyEditorDO", applyEditorDO);
        return "applynow_upload";
    }

    @PostMapping("/applynow_upload")
    public String applynow_upload(@RequestParam(value = "recruitNo") int recruitNo, @RequestParam(value = "edited_link") String edited_link,
                                  @RequestParam(value = "editor_memo") String editor_memo, HttpSession session, RedirectAttributes re) {
        String email = session.getAttribute("email").toString();
        try{
            applyService.applyUploadVideo(recruitNo, email, edited_link, editor_memo);
            re.addAttribute("msg", "저장 완료 되었습니다");
        }catch(Exception e){
            re.addFlashAttribute("errormsg", "유튜브 동영상 링크가 아닙니다.\n반드시 유튜브 동영상 링크를 넣어주세요");
            return "redirect:/applynow_upload?recruitNo="+recruitNo;
        }

        return "redirect:/applynow";
    }

    // 0512 준원
    // 유튜버 -> 작성한 구인글 -> 지원자 확인
    @GetMapping("/applier_check")
    public String bringMyRecruitApplier(int recruitNo, Model model) {

        RecruitDO recruitDO = recruitService.boardview(recruitNo);
        model.addAttribute("recruitDO",recruitDO);
        List<ApplierListDO> applierListDO = applyService.myApplierList(recruitNo);

        if (applierListDO.size() == 0) {
            model.addAttribute("NotApply", "해당 구인글에 지원자가 없습니다.");
        } else {
            model.addAttribute("applierListDO", applierListDO);
        }

        return "applier_check";
    }
}
