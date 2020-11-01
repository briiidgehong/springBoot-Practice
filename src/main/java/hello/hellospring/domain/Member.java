package hello.hellospring.domain;

import javax.persistence.*;

//이제부터 멤버는 JPA 가 관리하는 객체(=ENTITY) 이다.
//@Entity @Column 등의 애노테이션을 가지고 database랑 객체 자원이랑 Mapping !!!!!
@Entity
public class Member {

    //strategy = GenerationType.IDENTITY 아이덴티티티 전략 : DB가 하나씩 올려서 알아서 자동생성//
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 시스템이 정하는 ID
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}