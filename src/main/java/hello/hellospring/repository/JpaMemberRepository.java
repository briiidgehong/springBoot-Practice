package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Transactional
public class JpaMemberRepository implements MemberRepository{

    // jpa는 모든지 엔티티 메이저로 동작
    // build.gradle 에서 implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 상속받으면
    // 스프링이 자동으로 EntityManager 생성해준다.
    private final EntityManager em;

    // 이렇게 만들어진거를 아래와 같이 스프링으로부터 injection 받으면 된다 !
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();

    }

    @Override
    public List<Member> findAll() {
        // JPQL : 객체지향 쿼리 Member 처럼 객체, 더 정확히는 <스프링에서 현재 관리하고 있는 Entity>를 대상으로 select 할 수 있다.
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
