package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    // 멤버 변수가 static 선언 되있으므로, 새로운 인스턴스를 만들어도 하나의 db를 참조한다.
    // 이로인해 새로운 객체 인스턴스의 data clear 가 먹히게 된다.
    //MemoryMemberRepository repository = new MemoryMemberRepository();

    //서비스를 실행할 때마다
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        //DI : Dependency Injection // MemberService 입장에서 멤버 리포지토리를 넣어주는 것 // 의존성 주입
        memberService = new MemberService(memberRepository);
    }

    //test가 끝날때마다 리소스 제거
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        // 이러한 상황이 주어져서
        Member member = new Member();
        member.setName("hello");

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}