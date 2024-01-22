import { NativeStackNavigationOptions } from "@react-navigation/native-stack";
import Login from "../screens/auth/Login";
import Register from "src/screens/auth/Register";
import RegisterSuccess from "src/screens/auth/RegisterSuccess";
import ROUTE_NAMES from "./routeNames";

interface Route {
  name: string;
  component: any;
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
];

export default routes;
