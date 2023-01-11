package com.example.boottestfinal.controllers;

import com.example.boottestfinal.entities.User;
import com.example.boottestfinal.entities.UserToDoList;
import com.example.boottestfinal.serviecies.CalculatorService;
import com.example.boottestfinal.serviecies.TodosService;
import com.example.boottestfinal.serviecies.UserHelper;
import com.example.boottestfinal.serviecies.UserToDoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;


@Controller
public class HelloController {
    private final UserHelper userHelper;
    private final CalculatorService calculatorService;
    private final UserToDoService userToDoService;
    private final TodosService todosService;
    @Autowired
    public HelloController(UserHelper userHelper,
                           CalculatorService calculatorService,
                           UserToDoService userToDoService,
                           TodosService todosService) {
        this.userHelper = userHelper;
        this.calculatorService = calculatorService;
        this.userToDoService = userToDoService;
        this.todosService = todosService;
    }




    @PostMapping("/")
    public ModelAndView getAllUsers(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("hello");
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String error = "";
        if (age != null && name != null) {
            try {
                userHelper.addUser(new User(Integer.parseInt(age), name));
            } catch (Exception e) {
                error = "Not a valid age number";
            }
        }
        modelAndView.addObject("error", error);
        List<User> users = userHelper.findAllUsers();
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/")
    public String getForm(Model model) {
        List<User> users = userHelper.findAllUsers();
        model.addAttribute("users", users);
        return "hello";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id) {
        userHelper.deleteId(id);
        return "redirect:/";
    }

    @GetMapping(path = "{id}")
    public String userId(@PathVariable("id") int id, Model model) {
        User user = userHelper.getUser(id);
        if (user != null) model.addAttribute("oneUser", user);
        else model.addAttribute(model.addAttribute("oneUser", "NO USERS FOUND"));
        return "user";
    }

    @GetMapping("calculator")
    public String calculator(Model model, HttpServletRequest request) {
        if (request.getParameter("input") == null) return "redirect:/calculator?input=";
        String input = request.getParameter("input");
        model.addAttribute("input", input);
        String result;
        if (!input.equals("")) {
            try {
                result = calculatorService.calculate(input);
                if (!result.equals("error"))
                    model.addAttribute("result", result);
                else {
                    model.addAttribute("error", "WRONG EXPRESSION");
                    model.addAttribute("result", "Sorry, no result");
                }
            } catch (Exception e) {
                model.addAttribute("error", "WRONG EXPRESSION");
                model.addAttribute("result", "Sorry, no result");
            }
        } else {
            model.addAttribute("error", "Please input some values");
            model.addAttribute("result", "");
        }
        return "calculator";
    }

    @PostMapping("todos/login/check")
    public String checkLogin(@RequestParam("login") String login,
                             @RequestParam("password") String password,
                             HttpServletResponse response,
                             HttpSession session) {
        switch (userToDoService.checkPassword(login, password)) {
            case "success" -> {
                Cookie loginCookie = new Cookie("loggedIn", login);
                loginCookie.setMaxAge(3600);
                loginCookie.setPath("/todos");
                response.addCookie(loginCookie);
                session.setAttribute("loginError",
                        "successfully logged in!");
                return "redirect:/todos";
            }
            case "wrong password" -> {
                session.setAttribute("loginError",
                        "wrong password!");
                return "redirect:/todos/login";
            }
            case "does not exist" -> {
                session.setAttribute("loginError",
                        "user with such login does not exist!");
                return "redirect:/todos/login";
            }
        }
        return "redirect:/todos/login";
    }
    @PostMapping("todos/register/check")
    public String registerCheck(@RequestParam("login") String login,
                             @RequestParam("password") String password,
                             HttpServletResponse response,
                             HttpSession session) {
        if(!userToDoService.checkPresence(login)){
            userToDoService.save(login, password);
            Cookie loginCookie = new Cookie("loggedIn", login);
            loginCookie.setMaxAge(3600);
            loginCookie.setPath("/todos");
            response.addCookie(loginCookie);
            session.setAttribute("loginError",
                    "successfully registered!");
            return "redirect:/todos";
        } else {
            session.setAttribute("loginError",
                    "such login already exists!");
            return "redirect:/todos/register";
        }
    }
    @GetMapping("todos/login")
    public String login(Model model, HttpSession session){
         model.addAttribute("loginError",
                 session.getAttribute("loginError"));
         return "login";
    }
    @GetMapping("todos/register")
    public String register(Model model, HttpSession session){
        model.addAttribute("loginError",
                session.getAttribute("loginError"));
        return "register";
    }
    @GetMapping("/todos")
    public String todos(Model model, HttpServletRequest request, HttpSession session){
        model.addAttribute("currentDate", new Date().toString());
        if(session.getAttribute("loginError")!=null) {
            if (session.getAttribute("loginError").equals("successfully registered!")
                    || session.getAttribute("loginError").equals("successfully logged in!")) {
                model.addAttribute("loginError", session.getAttribute("loginError"));
            }
        }
        String login = getLoginFromCookies(request);
        if(login.equals("")) model.addAttribute("loggedIn", "not logged in");
        else model.addAttribute("loggedIn", login);
        if(userToDoService.getTodos(login)!=null&&!userToDoService.getTodos(login).equals("")) {
            model.addAttribute("todosList", todosService.getTodosList(login));
        }
        return "todos";
    }
    @PostMapping("todos/add")
    public String addTodos(@RequestParam("todo-text")String text, @RequestParam("todo-date")String date,
                           HttpServletRequest request){
        String login = getLoginFromCookies(request);
        if(login.equals("")) return "redirect:/todos";
        UserToDoList userToDoList = userToDoService.findById(login);
        if(date.equals("")) date = "No date";
        if(userToDoList.getTodos()==null)userToDoList.setTodos(text + "%d%"+date+"%s%");
        else userToDoList.setTodos(userToDoList.getTodos()  + text + "%d%"+date+"%s%");
        userToDoService.updateUser(userToDoList);
        return "redirect:/todos";
    }
    @PostMapping("todos/delete")
    public String deleteTodo(@RequestParam("currentTodoText")String text, HttpServletRequest request){
        String login = getLoginFromCookies(request);
        UserToDoList userToDoList = userToDoService.findById(login);
        String newString = userToDoList.getTodos().replace(text, "");
        userToDoList.setTodos(newString);
        userToDoService.updateUser(userToDoList);
        return "redirect:/todos";
    }
    public String getLoginFromCookies(HttpServletRequest request){
        Cookie[] cookies =  request.getCookies();
        String login = "";
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("loggedIn")) {
                    login = cookie.getValue();
                }
            }
        }
        return login;
    }
}
