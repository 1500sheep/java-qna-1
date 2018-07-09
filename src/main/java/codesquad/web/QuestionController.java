package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    // post 방식으로 받을 것이며, /users로 들어오게 되면 이 메서드를 실행하라는 뜻!
    @PostMapping("/questions")
    public String create(Question question) {
        //questions.add(question);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String create(Model model) {
        //model.addAttribute("questions", questions);
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable long id, Model model) {
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);
        return "/qna/show";
    }

    @GetMapping("/page/qna")
    public String pageQna() {
        return "/qna/form";
    }

    @GetMapping("/questions/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        Question question = questionRepository.findById(id).get();
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PostMapping("/questions/{id}/update")
    public String update(@PathVariable long id, Question question) {
        verifyQuestion(id, question);
        return "redirect:/questions/{id}";
    }

    private void verifyQuestion(long id, Question question) {
        if (questionRepository.findById(id).get().equals(question)) {
            questionRepository.save(question);
        }
    }

    @DeleteMapping("/questions/{id}")
    public String delete(@PathVariable long id, Question question) {
        questionRepository.delete(question);
        return "redirect:/";
    }
}
