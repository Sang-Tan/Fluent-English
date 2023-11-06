package com.fluentenglish.web.learningmaterial.lesson.admin;

import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.common.viewattribute.UserInfo;
import com.fluentenglish.web.learningmaterial.lesson.admin.mapper.ControllerLessonMapper;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonCreateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.request.LessonUpdateDto;
import com.fluentenglish.web.learningmaterial.lesson.admin.response.LessonDto;
import com.fluentenglish.web.learningmaterial.topic.admin.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/admin/lesson")
public class LessonController {
    private final TopicService topicService;

    private final LessonService lessonService;

    private final ControllerLessonMapper lessonMapper;

    public LessonController(TopicService topicService,
                            LessonService lessonService,
                            ControllerLessonMapper lessonMapper) {
        this.topicService = topicService;
        this.lessonService = lessonService;
        this.lessonMapper = lessonMapper;
    }

    @GetMapping("/create")
    public ModelAndView getCreateLessonPage(@RequestParam int topicId) {
        ModelAndView modelAndView = new ModelAndView("learningmaterial/lesson/create");
        if (!topicService.topicExistsById(topicId)) {
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }

        modelAndView.addObject("topicId", topicId);
        modelAndView.addObject("userInfo", getUserInfo());

        return modelAndView;
    }

    @PostMapping("/create")
    public RedirectView createLesson(@Valid LessonCreateDto lessonCreateDto,
                                     Errors errors,
                                     RedirectAttributes redirectAttributes) {
        try {
            validateUserInput(errors);
            lessonService.createLesson(lessonCreateDto);
            return new RedirectView("/admin/topic/" + lessonCreateDto.getTopicId());
        } catch (UserInputException e) {
            redirectAttributes.addFlashAttribute("submittedLesson", lessonCreateDto);
            redirectAttributes.addFlashAttribute("userInputErrors", e);
            return new RedirectView("/admin/lesson/create?topicId=" + lessonCreateDto.getTopicId());
        }
    }

    @GetMapping("/{lessonId}")
    public ModelAndView getLessonPage(@PathVariable int lessonId) {
        LessonDto lessonDto = lessonService.getLessonById(lessonId);
        String introduction = lessonService.getLessonIntroduction(lessonId);

        ModelAndView modelAndView = new ModelAndView("learningmaterial/lesson/detail");
        modelAndView.addObject("lesson", lessonDto);
        modelAndView.addObject("userInfo", getUserInfo());
        modelAndView.addObject("introduction", introduction);

        return modelAndView;
    }

    @PostMapping("/{lessonId}/update")
    public RedirectView updateLesson(@PathVariable int lessonId,
                                     @Valid LessonUpdateDto lessonUpdateDto,
                                     Errors errors,
                                     RedirectAttributes redirectAttributes) {
        try {
            validateUserInput(errors);
            lessonService.updateLesson(lessonId, lessonUpdateDto);
        } catch (UserInputException e) {
            redirectAttributes.addFlashAttribute("submittedLesson", lessonUpdateDto);
            redirectAttributes.addFlashAttribute("userInputErrors", e);
        }

        return new RedirectView("/admin/lesson/" + lessonId);
    }

    @PostMapping("/{lessonId}/set-publicity")
    public RedirectView setLessonPublicity(@PathVariable int lessonId,
                                           @RequestParam boolean isPublic) {
        lessonService.setLessonPublicity(lessonId, isPublic);

        return new RedirectView("/admin/lesson/" + lessonId);
    }

    @PostMapping("/{lessonId}/delete")
    public RedirectView deleteLesson(@PathVariable int lessonId) {
        int topicId = lessonService.getTopicIdOfLesson(lessonId);
        lessonService.deleteLesson(lessonId);

        return new RedirectView("/admin/topic/" + topicId);
    }

    @PostMapping("/{lessonId}/update-introduction")
    public RedirectView updateLessonIntroduction(@PathVariable int lessonId,
                                                 @RequestParam String content) {
        lessonService.updateLessonIntroduction(lessonId, content);

        return new RedirectView("/admin/lesson/" + lessonId);
    }

    private void validateUserInput(Errors errors) {
        List<InputErrorInfo> errorInfos = errors.getFieldErrors().stream()
                .map(error -> new InputErrorInfo(error.getField(), error.getDefaultMessage()))
                .toList();

        if (!errorInfos.isEmpty()) {
            throw new UserInputException(errorInfos);
        }
    }

    private UserInfo getUserInfo() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return UserInfo.builder()
                .username(authentication.getName())
                .build();
    }
}
