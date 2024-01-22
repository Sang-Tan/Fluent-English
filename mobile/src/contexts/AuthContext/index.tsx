import { Authentication, AuthenticationContext } from "./types";
import { createContext, useState, useEffect, useCallback } from "react";
import { ActivityIndicator } from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";

const AUTH_KEY = "auth";

const AuthContext = createContext<AuthenticationContext>({
  auth: null,
  setAuth: async () => {},
});

function AuthProvider({ children }: { children: React.ReactNode }) {
  const [auth, setAuth] = useState<Authentication | null>(null);
  const [loading, setLoading] = useState(true);

  const setAuthCallback = useCallback(
    (auth: Authentication | null) => {
      setAuth(auth);
      if (auth) {
        setAuthToLocalStorage(auth);
      } else {
        removeAuthFromLocalStorage();
      }
    },
    [setAuth]
  );

  useEffect(() => {
    getAuthFromLocalStorage().then((auth) => {
      setAuthCallback(auth);
      setLoading(false);
    });
  }, []);

  return (
    <AuthContext.Provider value={{ auth, setAuth: setAuthCallback }}>
      {loading ? <ActivityIndicator size="large" color="#0000ff" /> : children}
    </AuthContext.Provider>
  );
}

async function getAuthFromLocalStorage(): Promise<Authentication | null> {
  const auth = await AsyncStorage.getItem(AUTH_KEY);
  return auth ? JSON.parse(auth) : null;
}

async function setAuthToLocalStorage(auth: Authentication) {
  await AsyncStorage.setItem(AUTH_KEY, JSON.stringify(auth));
}

async function removeAuthFromLocalStorage() {
  await AsyncStorage.removeItem(AUTH_KEY);
}

export { AuthProvider };
export default AuthContext;
