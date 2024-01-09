import { LessonList, CreateLesson, LessonDetail } from "src/pages/lesson";
import Login from "src/pages/auth/Login";

const privateRoutes = [
  {
    path: "/",
  },
  {
    path: "/lesson",
    page: LessonList,
  },
  {
    path: "/lesson/create",
    page: CreateLesson,
  },
  {
    path: "/lesson/:lessonId",
    page: LessonDetail,
  },
  {
    path: "/learning-path",
  },
  {
    path: "/learning-material",
  },
  {
    path: "/learning-material-category",
  },
];

const publicRoutes = [
  {
    path: "/login",
    layout: null,
    page: Login,
  },
];

export { privateRoutes, publicRoutes };
