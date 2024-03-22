package com.fluentenglish.web.study.session.dao.quiz;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DataHolderQuizzesQueue implements SessionQuizzesQueue {
    private final LinkedList<Quiz> quizzes = new LinkedList<>();

    public DataHolderQuizzesQueue() {
    }

    public DataHolderQuizzesQueue(List<Quiz> quizzes) {
        this.quizzes.addAll(quizzes);
    }

    @Override
    public void add(List<Quiz> quizzes) {
        this.quizzes.addAll(quizzes);
    }

    @Override
    public void add(Quiz quiz) {
        quizzes.add(quiz);
    }

    @Override
    public int quizzesCount() {
        return quizzes.size();
    }

    @Override
    public Quiz poll() {
        return quizzes.poll();
    }

    @Override
    public Quiz peek() {
        return quizzes.peek();
    }

}
