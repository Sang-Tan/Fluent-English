import { NativeStackNavigationOptions } from "@react-navigation/native-stack";
import Login from "../screens/auth/Login";
import Register from "src/screens/auth/Register";
import RegisterSuccess from "src/screens/auth/RegisterSuccess";
import LessonSearch from "src/screens/learningMaterial/LessonSearch";
import LessonDetail from "src/screens/learningMaterial/LessonDetail";
import ROUTE_NAMES from "./routeNames";
import React from "react";

interface Route {
  name: string;
  component: React.FC<any>;
  options?: NativeStackNavigationOptions;
}

const routes: Route[] = [
  {
    name: ROUTE_NAMES.LOGIN,
    component: Login,
    options: {
      headerShown: false,
    },
  },
  {
    name: ROUTE_NAMES.REGISTER,
    component: Register,
    options: {
      headerShown: false,
    },
  },
  {
    name: ROUTE_NAMES.REGISTER_SUCCESS,
    component: RegisterSuccess,
    options: {
      headerShown: false,
    },
  },
  {
    name: ROUTE_NAMES.LESSON_SEARCH,
    component: LessonSearch,
    options: {
      headerShown: false,
    },
  },
  {
    name: ROUTE_NAMES.LESSON_DETAIL,
    component: LessonDetail,
    options: {
      headerShown: false,
    },
  },
];

export default routes;
