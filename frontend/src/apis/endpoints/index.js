export const ENDPOINT_LESSON = Object.freeze({
  LIST: "/lessons",
  ADD: "/lessons",
  DETAIL: "/lessons/:lessonId",
  UPDATE: "/lessons/:lessonId",
  DELETE: "/lessons/:lessonId",
  SET_PUBLICITY: "/lessons/:lessonId/publicity",
});

export const ENDPOINT_EXERCISE = Object.freeze({
  LIST: "/lessons/:lessonId/exercises",
  ADD: "/exercises",
  DETAIL: "/exercises/:exerciseId",
  UPDATE: "/exercises/:exerciseId",
  DELETE: "/exercises/:exerciseId",
  SET_PUBLICITY: "/exercises/:exerciseId/publicity",
});
