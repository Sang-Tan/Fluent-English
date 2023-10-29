package com.fluentenglish.web.learningmaterial.topic.admin;

import com.fluentenglish.web.common.exception.userinput.InputErrorInfo;
import com.fluentenglish.web.common.exception.userinput.UserInputException;
import com.fluentenglish.web.common.viewattribute.DataTable;
import com.fluentenglish.web.common.viewattribute.DataTableRow;
import com.fluentenglish.web.common.viewattribute.UserInfo;
import com.fluentenglish.web.learningmaterial.topic.admin.mapper.ControllerTopicMapper;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicCreateUpdateDto;
import com.fluentenglish.web.learningmaterial.topic.admin.request.TopicSearchDto;
import com.fluentenglish.web.learningmaterial.topic.admin.response.TopicDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/admin/topic")
public class TopicController {
    private final TopicService topicService;

    private final ControllerTopicMapper topicMapper;

    public TopicController(TopicService topicService, ControllerTopicMapper topicMapper) {
        this.topicService = topicService;
        this.topicMapper = topicMapper;
    }

    @GetMapping
    public ModelAndView index() {
        List<TopicDto> topics = topicService.getAllTopics();
        List<DataTableRow> rows = topics.stream().map(topic -> DataTableRow.builder()
                .id(topic.getId())
                .name(topic.getName())
                .updateUrl("/admin/topic/" + topic.getId())
                .deleteUrl("/admin/topic/" + topic.getId() + "/delete")
                .build()).toList();

        DataTable dataTable = DataTable.builder()
                .isArrangeable(false)
                .isPaginable(true)
                .changePositionsUrl("/admin/topic/change-positions")
                .rows(rows)
                .build();

        ModelAndView modelAndView = new ModelAndView("learningmaterial/topic/index");
        modelAndView.addObject("topicDataTable", dataTable);
        modelAndView.addObject("userInfo", getUserInfo());

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getCreateTopicPage() {
        ModelAndView modelAndView =
                new ModelAndView("learningmaterial/topic/create");
        modelAndView.addObject("userInfo", getUserInfo());

        return modelAndView;
    }

    @GetMapping("/search-json")
    @ResponseBody
    public List<TopicDto> searchTopic(TopicSearchDto topicSearchDto) {
        return topicService.searchTopics(topicSearchDto);
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView createTopic(@Valid TopicCreateUpdateDto topicDto,
                                    Errors errors,
                                    RedirectAttributes redirectAttributes) {
        try {
            validateUserInput(errors);
            topicService.createTopic(topicDto);
        } catch (UserInputException e) {
            redirectAttributes.addFlashAttribute("submittedTopic", topicDto);
            redirectAttributes.addFlashAttribute("userInputErrors", e);
            return new RedirectView("/admin/topic/create");
        }

        return new RedirectView("/admin/topic");
    }

    @GetMapping("/{id}")
    public ModelAndView getTopicDetailPage(@PathVariable int id, Model model) {
        TopicDto topic = topicService.getTopicById(id);

        if (model.containsAttribute("submittedTopic")) {
            topicMapper.updateTopicDtoFromCreateUpdateDto((TopicCreateUpdateDto) model.getAttribute("submittedTopic"), topic);
        }

        ModelAndView modelAndView = new ModelAndView("learningmaterial/topic/detail");
        modelAndView.addObject("topic", topic);
        modelAndView.addObject("userInfo", getUserInfo());

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public RedirectView updateTopic(@PathVariable int id,
                                    @Valid TopicCreateUpdateDto topicDto,
                                    Errors errors,
                                    RedirectAttributes redirectAttributes) {
        try {
            validateUserInput(errors);
            topicService.updateTopic(id, topicDto);
        } catch (UserInputException e) {
            redirectAttributes.addFlashAttribute("submittedTopic", topicDto);
            redirectAttributes.addFlashAttribute("userInputErrors", e);
            return new RedirectView("/admin/topic/" + id);
        }

        return new RedirectView("/admin/topic/" + id);
    }

    @PostMapping("/{id}/set-publicity")
    public String setPublicity(@PathVariable int id,
                               @RequestParam("is-public") boolean isPublic) {
        topicService.setTopicPublicity(id, isPublic);

        return "redirect:/admin/topic/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteTopic(@PathVariable int id) {
        topicService.deleteTopic(id);

        return "redirect:/admin/topic";
    }

    private UserInfo getUserInfo() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return UserInfo.builder()
                .username(authentication.getName())
                .build();
    }

    private void validateUserInput(Errors errors) {
        List<InputErrorInfo> errorInfos = errors.getFieldErrors().stream()
                .map(error -> new InputErrorInfo(error.getField(), error.getDefaultMessage()))
                .toList();

        if (!errorInfos.isEmpty()) {
            throw new UserInputException(errorInfos);
        }
    }
}
