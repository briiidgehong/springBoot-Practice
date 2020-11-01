package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// @Controller run 동작시에, MemberController 객체를 생성해서 스프링 컨테이너에 넣어두고, 관리한다.
// 이걸 스프링 컨테이너에서 스프링 빈(컨트롤러 객체)이 관리된다고 한다.
@Controller
public class MemberController {
    private final MemberService memberService ;


    // 매번 기능을 쓸때마다 여러군대에서 new 를 하면 다른 객체이기 때문에 문제가 생김
    // 스프링 컨테이너에 등록을 해놓고 쓰면된다. // 스프링 컨테이너에 딱 하나만 등록된다.
    // 부과적인 효과도 여러가지 볼수 있다.
    // 생성자로 만들어준다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        // MemberForm form :
        // <input type="text" id="name" name="name" placeholder="이름을 입력하세요">
        // name 의 name 과 아래 필드의 name 을 스프링에서 연결시켜줌
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member =" + member.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        //ctrl + alt + v
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
