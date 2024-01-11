package com.fluentenglish.web.learningmaterial.quiz.admin;

import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizCreateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.request.QuizUpdateDto;
import com.fluentenglish.web.learningmaterial.quiz.admin.response.QuizDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/admin/api/quizzes")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/{quizId}")
    public QuizDto getQuizById(@PathVariable int quizId) {
        return quizService.getQuizById(quizId);
    }

    @PostMapping
    public ResponseEntity<Void> createQuiz(@RequestBody @Valid QuizCreateDto quizCreateDto) {
        int quizId = quizService.createQuiz(quizCreateDto);

        return ResponseEntity.created(URI.create(String.format("/admin/api/quizzes/%d", quizId))).build();
    }

    @PutMapping("/{quizId}")
    public void updateQuiz(@PathVariable int quizId, @RequestBody QuizUpdateDto quizUpdateDto) {
        quizService.updateQuiz(quizId, quizUpdateDto);
    }

    @DeleteMapping("/{quizId}")
    public void deleteQuiz(@PathVariable int quizId) {
        quizService.deleteQuiz(quizId);
    }
}
