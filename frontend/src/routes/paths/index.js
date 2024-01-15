export const PATH_LESSON = Object.freeze({
  LIST: "/lesson",
  DETAIL: "/lesson/:lessonId",
  CREATE: "/lesson/create",
});

export const PATH_EXERCISE = Object.freeze({
  DETAIL: "/exercise/:exerciseId",
  CREATE: "/lesson/:lessonId/create-exercise",
});
