package com.fluentenglish.web.study.session.context;

import org.springframework.stereotype.Component;

@Component
public class StudySessionContext {
    private final ThreadLocal<Boolean> threadLocalInTransaction =
            ThreadLocal.withInitial(() -> false);

    private final ThreadLocal<DataHolderStudySessionContainer> threadLocalSessionContainer
            = new ThreadLocal<>();

    public void beginTransaction() {
        threadLocalInTransaction.set(true);
        threadLocalSessionContainer.set(new DataHolderStudySessionContainer());
    }

    public void endTransaction() {
        threadLocalInTransaction.set(false);
        threadLocalSessionContainer.remove();
    }

    public boolean inTransaction() {
        return threadLocalInTransaction.get();
    }

    public DataHolderStudySessionContainer getContainer() {
        if (!inTransaction()) {
            throw new IllegalStateException("Not in transaction");
        }

        return threadLocalSessionContainer.get();
    }
}
