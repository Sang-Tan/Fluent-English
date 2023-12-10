import Login from "src/pages/auth/Login";

const privateRoutes = [
  {
    path: "/",
  },
  {
    path: "/topic",
  },
  {
    path: "/topic/create",
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
