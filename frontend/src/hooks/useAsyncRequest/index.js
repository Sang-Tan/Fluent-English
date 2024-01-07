import { useContext, useCallback, useState } from "react";
import { AuthContext } from "src/contexts/AuthContext";

const API_URL = process.env.REACT_APP_API_URL;
/**
 * @callback RequestFunction
 * @param {string} url
 * @param {object} options
 * @returns {Promise<Response>}
 */
/**
 * @returns {[RequestFunction, boolean]} [request, loading]
 */
function useAsyncRequest({ initialLoading = false } = {}) {
  const [authInfo, setAuthInfo] = useContext(AuthContext);
  const [loading, setLoading] = useState(initialLoading);

  const request = useCallback(
    /**
     * @param {string} url
     * @param {object} options
     */
    async (url, options) => {
      const headers = options?.headers || {};

      if (authInfo?.isAuthenticated) {
        headers.Authorization = `Bearer ${authInfo.token}`;
      }

      setLoading(true);
      try {
        const resp = await fetch(`${API_URL}${url}`, {
          ...options,
          headers,
        });

        if (resp.status === 401 || resp.status === 403) {
          setAuthInfo({
            isAuthenticated: false,
            token: null,
          });
          throw new Error("Unauthorized");
        }

        setLoading(false);
        return resp;
      } catch (error) {
        setLoading(false);
        throw error;
      }
    },
    [authInfo, setAuthInfo]
  );

  return [request, loading];
}

export default useAsyncRequest;
