import { createContext, useState, useCallback } from "react";

const AuthContext = createContext(null);

function AuthContextProvider({ children }) {
  const [authInfo, setAuthInfo] = useState(getAuthInfoFromStorage());

  const setAuthInfoWrapper = useCallback((authInfo) => {
    localStorage.setItem("authInfo", JSON.stringify(authInfo));
    setAuthInfo(authInfo);
  }, []);

  return (
    <AuthContext.Provider value={[authInfo, setAuthInfoWrapper]}>
      {children}
    </AuthContext.Provider>
  );
}

function getAuthInfoFromStorage() {
  const authInfo = localStorage.getItem("authInfo");

  if (authInfo) {
    return JSON.parse(authInfo);
  } else {
    return {
      isAuthenticated: false,
    };
  }
}

export { AuthContext, AuthContextProvider };
