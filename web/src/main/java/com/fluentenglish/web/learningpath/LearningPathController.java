package com.fluentenglish.web.learningpath;

import com.fluentenglish.web.common.form.Alert;
import com.fluentenglish.web.common.form.AlertType;
import com.fluentenglish.web.learningpath.exception.LearningPathAlreadyExistsException;
import com.fluentenglish.web.learningpath.exception.LearningPathNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/learning-path")
@Slf4j
@RequiredArgsConstructor
public class LearningPathController {
    private final LearningPathService learningPathService;
    private final LearningPathMapper learningPathMapper;

    @GetMapping(value = "/{learningPathId}")
    public String getLearningPath(@ModelAttribute(value = "alert") Alert alert,
                                  @PathVariable("learningPathId") Integer learningPathId,
                                  Model model
    ) {
        log.debug("get learning path info by id @" + learningPathId);
        try {
            LearningPath learningPath = learningPathService.getLearningPath(learningPathId);
            log.debug("learning path info @" + learningPath);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute("learningPath", learningPath);
            model.addAttribute("authentication", authentication);
            model.addAttribute("alert", alert);
            return "learningpath/details_learning_path";
        }catch (LearningPathNotFoundException e){
            log.error("learning path not found",e);

            alert.setType(AlertType.FAILURE);
            alert.setMessage("Learning path not found");
            return "redirect:/admin/learning-path";
        }
    }

    @GetMapping
    public String getAllLearningPaths(@ModelAttribute(value = "alert") Alert alert,Model model) {

        log.debug("get all learning paths");

        List<LearningPath> learningPaths = learningPathService.getAllLearningPath();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("learningPaths", learningPaths);
        model.addAttribute("authentication", authentication);
        model.addAttribute("alert", alert);
        model.addAttribute("learningPathDataTable",learningPathMapper.toDataTable(learningPaths));

        return "learningpath/learning_paths";
    }

    @GetMapping("/create")
    public String getCreateLearningPathForm(@ModelAttribute("alert") Alert alert,
                                            @ModelAttribute("previous-submit-form") LearningPathForm form
                                            , Model model) {

        log.debug("get create learning path form @" + form);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authentication", authentication);
        model.addAttribute("learningPathForm", form);
        model.addAttribute("alert", alert);

        return "learningpath/create_learning_path";
    }

    @PostMapping("/create")
    public String createLearningPath(@ModelAttribute("learning-path-form") LearningPathForm form,
                                     RedirectAttributes redirectAttributes) {

        log.debug("create a new learning path @" + form);

        try {
            LearningPath learningPathSaved = learningPathService.createLearningPath(form);
            log.debug("create learning path -> " + learningPathSaved);

            Alert alert = Alert.builder()
                    .type(AlertType.SUCCESS)
                    .message("Learning Path " + learningPathSaved.getName() + " created successfully")
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);

            return "redirect:/admin/learning-path/" + learningPathSaved.getId();
        }
        catch (LearningPathAlreadyExistsException e) {

            log.debug("learning path already exists");

            Alert alert = Alert.builder()
                    .type(AlertType.FAILURE)
                    .message(e.getMessage())
                    .build();
            redirectAttributes.addFlashAttribute("previous-submit-form", form);
            redirectAttributes.addFlashAttribute("alert", alert);

            return "redirect:/admin/learning-path/create";
        }
    }

    @PostMapping("/{learningPathId}/update")
    public String updateLearningPath(@PathVariable("learningPathId") Integer learningPathId,
                                     @ModelAttribute("learning-path-form") LearningPathForm form,
                                     RedirectAttributes redirectAttributes) {
        log.debug("update learning path by id @" + learningPathId + " with form content @" + form);

        try {
            LearningPath learningPathSaved = learningPathService.updateLearningPath(learningPathId,form);
            log.debug("update learning path -> " + learningPathSaved);

            Alert alert = Alert.builder()
                    .type(AlertType.SUCCESS)
                    .message("Learning Path " + learningPathSaved.getName() + " updated successfully")
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);

            return "redirect:/admin/learning-path/" + learningPathId;
        }
        catch (LearningPathNotFoundException e)
        {
            log.debug("learning path not found");

            Alert alert = Alert.builder()
                    .type(AlertType.FAILURE)
                    .message(e.getMessage())
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);

            return "redirect:/admin/learning-path";
        }
        catch (LearningPathAlreadyExistsException e) {

            log.debug("learning path already exists");

            Alert alert = Alert.builder()
                    .type(AlertType.FAILURE)
                    .message(e.getMessage())
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);

            return "redirect:/admin/learning-path/" + learningPathId;
        }
    }

    @PostMapping("/{learningPathId}/delete")
    public String deleteLearningPath(@PathVariable("learningPathId") Integer learningPathId, RedirectAttributes redirectAttributes) {
        log.debug("delete learning path by id @" + learningPathId);

        try {
            learningPathService.deleteLearningPath(learningPathId);
            log.debug("delete learning path -> " + learningPathId);

            Alert alert = Alert.builder()
                    .type(AlertType.SUCCESS)
                    .message("Learning Path deleted successfully")
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);
        }
        catch (LearningPathNotFoundException e)
        {
            log.debug("learning path not found");

            Alert alert = Alert.builder()
                    .type(AlertType.FAILURE)
                    .message(e.getMessage())
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);
        }

        return "redirect:/admin/learning-path";
    }

    @PostMapping("/{learning-path-id}/set-publicity")
    public String setPublicity(@ModelAttribute("set-publicity-form") LearningPathForm form,
                               @PathVariable("learning-path-id") Integer learningPathId,
                               RedirectAttributes redirectAttributes) {
        log.debug("set learning path publicity by id @" + learningPathId + " with form content @" + form);
        try {
            LearningPath saved = learningPathService.setLearningPathPublicity(learningPathId,form.getIsPublic());
            log.debug("set learning path publicity -> " + learningPathId);

            Alert alert = Alert.builder()
                    .type(AlertType.SUCCESS)
                    .message("Learning Path " + saved.getName() + " set to " + (saved.isPublic() ? "public" : "private") + " successfully")
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);
            return "redirect:/admin/learning-path/" + learningPathId;
        }
        catch (LearningPathNotFoundException e)
        {
            log.debug("learning path id@" + learningPathId + " not found");

            Alert alert = Alert.builder()
                    .type(AlertType.FAILURE)
                    .message(e.getMessage())
                    .build();
            redirectAttributes.addFlashAttribute("alert", alert);

            return "redirect:/admin/learning-path";
        }
    }

}
