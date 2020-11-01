package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional //JPA 는 데이터를 변경할때는 무조건 TRANSACTION 안에서 실행되야 하므로 해당 애노테이션이 필요하다.
public class MemberService {
    //test 의 경우, ctrl+shift+T -> Choose test for MemberService 로, 손쉽게 testCase 작성 가능 !

    // new 하는 것이 아닌 외부에서 넣어주도록 ! 왜냐 하나의 인스턴스 객체로 쭈욱 들고다니기 위해서
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입 (비즈니스에 의존적으로 용어 설정)
     */
    public Long join(Member member) {
        // logic : 중복이름의 회원이 있으면 안된다.

        //로직이 이렇게 있는 경우, method로 빼주는게 좋음 -> refactor this -> c + a + shift + t
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        // ctrl + alt + v -> return 값 받아주는거 자동 생성 : Optional<Member> byName =
        // optional 은 null check 의 현대버전이라고 생각하면 된다. wrapper class 로써, null 과 관련된 다양한 method 를 제공한다.
        /*
        Optional<Member> result = memberRepository.findByName(member.getName());
        //return 값이 optional 이므로 다음과 같이 null 처리가 가능함
        result.ifPresent(m -> {
            //ifPresent = 이름이 있으면 !
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
         */
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
            //ifPresent = 이름이 있으면 !
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 전체 회원 조회 (비즈니스에 의존적으로 용어 설정)
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
