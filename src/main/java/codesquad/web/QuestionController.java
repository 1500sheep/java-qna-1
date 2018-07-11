package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.exception.QuestionNotFoundException;
import codesquad.exception.UserNotFoundException;
import codesquad.exception.UserNotInSessionException;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.domain.User;
import codesquad.repository.UserRepository;
import codesquad.util.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "/index";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        User user = getUser(session);
        question.setWriter(user);
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String createPost() {
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        List<Answer> answers = answerRepository.findAllByQuestionIdAndIsDeletedFalse(id);
        model.addAttribute("question", questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new));
        model.addAttribute("answers", answers);
        model.addAttribute("num", answers.size());

        return "/qna/show";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question newQuestion) {
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.update(newQuestion);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = getUser(session);
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.checkWriter(user);
        questionRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = getUser(session);
        Question question = questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.checkWriter(user);
        question.setWriter(user);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        User user = getUser(session);
        Question question = questionRepository.findById(questionId).orElseThrow(QuestionNotFoundException::new);
        answer.setWriter(user);
        answer.setQuestion(question);
        answerRepository.save(answer);
        return "redirect:/questions/{qid}";
    }

    @DeleteMapping("/{id}/answers/{answerId}")
    public String deleteAnswer(@PathVariable("id") Long id, @PathVariable("answerId") Long answerId, HttpSession session) {
        User user = getUser(session);
        Answer answer = answerRepository.findById(answerId).orElseThrow(IllegalAccessError::new);
        answer.deleteByUser(user);
        answerRepository.save(answer);
        return "redirect:/questions/{id}";
    }

    private User getUser(HttpSession session) {
        Long uid = SessionHandler.getId(session).orElseThrow(UserNotInSessionException::new);
        return userRepository.findById(uid).orElseThrow(UserNotFoundException::new);
    }

}
