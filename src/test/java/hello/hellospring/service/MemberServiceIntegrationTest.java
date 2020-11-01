package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //테스트를 시작할때, transaction을 실행하고, 테스트가 끝나면 전상태로 롤백을 해준다. // 앞선 DB 초기화의 역할 과 같다.
class MemberServiceIntegrationTest {

    //스프링 컨테이너한태 멤버 서비스, 멤버 리포지토리 내놔
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;


    @Test
    void 회원가입() {
        //given
        // 이러한 상황이 주어져서
        Member member = new Member();
        member.setName("hello123");

        //when
        // 이렇게 했을때
        Long saveId = memberService.join(member);

        //then
        // 이렇게 나오면 되
        Member findMember = memberService.findOne(saveId).get();
        //findOne의 return 값이 optional 이므로, 널체크도 가능하고, .get 사용 가능 !
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        //validateDuplicateMember 에서 예외가 터진다.
        memberService.join(member1);
        // () -> memberService.join(member2) 이 로직을 실행할껀대, IllegalStateException 이 예외가 터져야 한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /* try-catch 로 받는 경우
        try{
            memberService.join(member2);
            fail("예외 발생해야 하는대 발생 안함");
        }catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */


        //then

    }
}