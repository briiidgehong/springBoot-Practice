package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello spring !!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-api")
    @ResponseBody //http protocol 에서의 응답은 -> [header / body] 로 나뉘는대 이 body 부에 return 데이터를 직접 넣어주겠다.
    public  String helloAPI(@RequestParam("name") String name){
        return "hello" + name ;
    }

    @GetMapping("hello-api2")
    @ResponseBody
    public Hello helloAPI2(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 문자가 아니라 객체가 넘어감 -> 객체를 넘기면 json 으로 클라이언트에 넘기도록 spring 의 default 값으로 설정되어 있다.
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
