import { useContext, useState, useCallback } from "react";
import { AuthContext } from "src/contexts/AuthContext";

const API_URL = process.env.REACT_APP_API_URL;

function useRequest({ initialData = null, initialLoading = false } = {}) {
  const [authInfo, setAuthInfo] = useContext(AuthContext);
  const [data, setData] = useState(initialData);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(initialLoading);

  const request = useCallback(
    async (url, options) => {
      setLoading(true);
      const headers = options?.headers || {};

      if (authInfo?.isAuthenticated) {
        headers.Authorization = `Bearer ${authInfo.token}`;
      }

      const response = await fetch(`${API_URL}${url}`, {
        ...options,
        headers,
      });

      const responseData = await response.json();

      if (response.status === 401 || response.status === 403) {
        setAuthInfo({
          isAuthenticated: false,
          token: null,
        });
      }

      if (response.ok) {
        setData(responseData);
      } else {
        setError(responseData);
      }

      setLoading(false);

      return responseData;
    },
    [authInfo, setAuthInfo]
  );

  return {
    data,
    error,
    loading,
    request,
  };
}

export default useRequest;
