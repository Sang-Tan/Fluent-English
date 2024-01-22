import { NativeStackNavigationOptions } from "@react-navigation/native-stack";
import Login from "../screens/Login";
import ROUTE_NAMES from "./routeNames";

interface Route {
  name: string;
  component: React.FC;
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
];

export default routes;
