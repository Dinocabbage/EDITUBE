package com.example.testproject.controller;

import com.example.testproject.domain.Member;
import com.example.testproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.DocFlavor;
import java.util.List;

@Controller
public class MemberControllerr {

    private final MemberService memberService;

    @Autowired
    public MemberControllerr(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String createForm(){
        return "/createMemberForm";
    }

    @PostMapping("/new")
    public String create(MemberForm form) {
        System.out.println(form);
        Member member = new Member();
        System.out.println(member);
        member.setName(form.getId());
        member.setName(form.getAddress());
        member.setName(form.getName());
        member.setName(form.getNickName());
        System.out.println(member);

        memberService.join(member);

        return "redirect:/";
    }
    @GetMapping("memberManage")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "memberManage";
    }


}
