import { TopicsList, CreateTopic, UpdateTopic } from "src/pages/topic";
import Login from "src/pages/auth/Login";

const privateRoutes = [
  {
    path: "/",
  },
  {
    path: "/topic",
    page: TopicsList,
  },
  {
    path: "/topic/create",
    page: CreateTopic,
  },
  {
    path: "/topic/:topicId",
    page: UpdateTopic,
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
