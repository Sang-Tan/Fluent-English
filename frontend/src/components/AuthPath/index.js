import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "src/contexts/AuthContext";

function AuthPath({ children }) {
  const [authInfo] = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!authInfo.isAuthenticated) {
      navigate("/login");
    }
  }, [authInfo, navigate]);

  return authInfo.isAuthenticated ? children : <div>Redirecting...</div>;
}

export default AuthPath;
