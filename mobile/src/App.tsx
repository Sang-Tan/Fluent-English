import { RootStackParamList } from "./routes/types";

import ROUTE_NAMES from "./routes/routeNames";
import routes from "./routes";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import { AuthProvider } from "./contexts/AuthContext";
import React from "react";

const Stack = createNativeStackNavigator<RootStackParamList>();

function App() {
  return (
    <AuthProvider>
      <NavigationContainer>
        <Stack.Navigator initialRouteName={ROUTE_NAMES.LOGIN}>
          {routes.map((route) => (
            <Stack.Screen
              key={route.name}
              name={route.name}
              component={route.component}
              options={route.options}
            />
          ))}
        </Stack.Navigator>
      </NavigationContainer>
    </AuthProvider>
  );
}

export default App;
