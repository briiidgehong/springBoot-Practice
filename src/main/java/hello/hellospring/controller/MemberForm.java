package hello.hellospring.controller;

public class MemberForm {
    //<input type="text" id="name" name="name" placeholder="이름을 입력하세요">
    // name 의 name 과 아래 필드의 name 을 스프링에서 연결시켜줌
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
