import { LessonList, CreateLesson, LessonDetail } from "src/pages/lesson";
import { LearningPathDetail, CreateLearningPath, LearningPathList } from "src/pages/learningpath";
import { QuizDetail, CreateQuiz } from "src/pages/quiz";
import { ExerciseDetail } from "src/pages/exercise";
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
    path: "/learning-path/create",
    page: CreateLearningPath
  },
  {
    path: "/learning-path",
    page: LearningPathList
  },
  {
    path: "/learning-path/:learningPathId",
    page: LearningPathDetail
  },
  {
    path: "/learning-material",
  },
  {
    path: "/learning-material-category",
  },
  {
    path: "/exercise/:exerciseId",
    page: ExerciseDetail,
  },
  {
    path: "/quiz/create",
    page: CreateQuiz,
  },
  {
    path: "/quiz/:quizId",
    page: QuizDetail,
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
