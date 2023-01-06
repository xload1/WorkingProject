package com.example.boottestfinal;

import com.example.boottestfinal.enities.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@Controller
public class HelloController {
    @GetMapping("/")
    public String test(){
        return "test";
    }
//    UserHelper userHelper;
//    @Autowired
//    public void setUserHelper(UserHelper userHelper) {
//        this.userHelper = userHelper;
//    }

//    @GetMapping("/")
//    public ModelAndView hello(HttpServletRequest request){
//        ModelAndView modelAndView = new ModelAndView("hello");
//        String error = "";
//        String name;
//        int age;
//        if(request.getParameter("name")!=null&&request.getParameter("age")!=null) {
//            try {
//                name = request.getParameter("name");
//                age = Integer.parseInt(request.getParameter("age"));
//                userHelper.addUser(new User(age, name));
//            } catch (NumberFormatException e) {
//                error = "Wrong age value!";
//            }
//        }
//        List<User> users= userHelper.findAllUsers();
//        modelAndView.addObject("users", users);
//        modelAndView.addObject("error", error);
//        return modelAndView;
//    }

}
