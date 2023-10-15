package com.fluentenglish.web.learningpath;

import com.fluentenglish.web.common.form.DataTable;
import com.fluentenglish.web.common.form.DataTableRow;
import com.fluentenglish.web.learningmaterial.topic.TopicNotFoundException;
import com.fluentenglish.web.learningmaterial.topic.TopicRepository;
import com.fluentenglish.web.learningpath.detail.LearningPathDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component("learningPathMapper")
public class LearningPathMapper{
    private TopicRepository topicRepository;
    public LearningPath toLearningPath(LearningPathForm form) {
        LearningPath learningPath = new LearningPath();
        learningPath.setName(form.getName());
        learningPath.setDescription(form.getDescription());
//        learningPath.setPublic(form.getIsPublic());
//        form.getTopicIds().forEach(topicId ->
//            learningPath.getLearningPathDetails().add(LearningPathDetail.builder()
//                    .topic(topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId)))
//                    .learningPath(learningPath)
//                    .build())
//        );
        return learningPath;
    }
    public DataTable toDataTable(List<LearningPath> learningPaths) {
        return DataTable.builder()
                .isArrangeable(false)
                .isPaginable(true)
                .rows(learningPaths.stream().map(this::toDataTableRow).toList())
                .build();
    }
    public DataTableRow toDataTableRow(LearningPath learningPath) {
        return DataTableRow.builder()
                .name(learningPath.getName())
                .id(learningPath.getId())
                .updateUrl("/admin/learning-path/" + learningPath.getId())
                .deleteUrl("/admin/learning-path/" + learningPath.getId() + "/delete")
                .build();
    }
}
