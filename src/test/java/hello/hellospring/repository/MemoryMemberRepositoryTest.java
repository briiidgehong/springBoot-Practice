package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //test가 끝날때마다 리소스 제거
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save() {
        //save() 이 잘 동장하는지 test
        //main method 쓰는거랑 비슷하게 하면된다.
        Member member = new Member();
        member.setName("spring"); // ctrl + shift + enter

        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        //System.out.println("result =" + (result == member));
        //Assertions.assertEquals(member, result);
        // alt + enter : static import
        // ctrl + enter : intelli 자동완성
        assertThat(result).isEqualTo(member);
        // 실무에서는 빌드툴과 엮어서, test case 통과하지 않으면, 다음단계로 못넘어가게 막아버린다.
    }

    @Test
    public void findByName(){
        //findByName() 이 잘 동장하는지 test
        // spring1, spring2 회원가입
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
        //assertThat(result).isEqualTo(member2);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
