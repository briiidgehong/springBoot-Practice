package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //저장소에 저장
    Optional<Member> findById(Long id); // return 값 null 처리때문에 optional 쓴다.
    Optional<Member> findByName(String name); //findById나 findByName 을 통해 찾아올수 있다.
    List<Member> findAll(); // 지금까지 저장된 모든 회원들의 list 반환
}
