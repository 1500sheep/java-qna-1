package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // post 방식으로 받을 것이며, /users로 들어오게 되면 이 메서드를 실행하라는 뜻!
    @PostMapping("/users")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String create(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PostMapping("/users/{id}/update")
    public String update(@PathVariable long id, User user) {
        verifyUser(id, user);
        return "redirect:/users";
    }

    private void verifyUser(long id, User user) {
        if (userRepository.findById(id).get().equals(user)) {
            userRepository.save(user);
        }
    }

    @GetMapping("/page/user/login")
    public String pageUserLogin() {
        return "/user/login";
    }

    @GetMapping("/page/user/form")
    public String pageUserForm() {
        return "/user/form";
    }

//    @PostMapping("/users")
//    public String create(String userId, String password, String name, String email, Model model) {
//        User user = new User(userId, password, name, email);
//        users.add(user);
//        model.addAttribute("users",users);
//        return "/user/list";
//    }
//    @PostMapping("/users")
//    public ModelAndView create2(String userId, String password, String name, String email) {
//        User user = new User(userId, password, name, email);
//        users.add(user);
//        ModelAndView mav =new ModelAndView("/user/list");
//        mav.addObject("users",users);
//        return mav;
//    }
}
