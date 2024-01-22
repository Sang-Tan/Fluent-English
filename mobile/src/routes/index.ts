import { NativeStackNavigationOptions } from "@react-navigation/native-stack";
import Login from "../screens/Login";
import Register from "src/screens/Register";
import RegisterSuccess from "src/screens/RegisterSuccess";
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
