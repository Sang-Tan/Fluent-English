export const ENDPOINT_LESSON = Object.freeze({
  LIST: "/lessons",
  ADD: "/lessons",
  DETAIL: "/lessons/:lessonId",
  UPDATE: "/lessons/:lessonId",
  DELETE: "/lessons/:lessonId",
});

export const ENDPOINT_EXERCISE = Object.freeze({
  LIST: "/lessons/:lessonId/exercises",
  ADD: "/lessons/:lessonId/exercises",
  DETAIL: "/exercises/:exerciseId",
  UPDATE: "/exercises/:exerciseId",
  DELETE: "/exercises/:exerciseId",
});
