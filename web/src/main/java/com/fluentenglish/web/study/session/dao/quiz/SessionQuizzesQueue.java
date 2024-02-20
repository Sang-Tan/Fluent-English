package com.fluentenglish.web.study.session.dao.quiz;

import java.util.List;

public interface SessionQuizzesQueue {
    void add(List<Quiz> quizzes);

    void add(Quiz quiz);

    int quizzesCount();

    /**
     * Get the next quiz from the queue and remove it from the queue
     * @return the next quiz from the queue, or null if the queue is empty
     */
    Quiz poll();

    /**
     * Get the next quiz from the queue without removing it from the queue
     * @return the next quiz from the queue, or null if the queue is empty
     */
    Quiz peek();
}
