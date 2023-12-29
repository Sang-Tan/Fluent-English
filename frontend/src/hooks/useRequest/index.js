import { useContext, useCallback, useState } from "react";
import { AuthContext } from "src/contexts/AuthContext";

const API_URL = process.env.REACT_APP_API_URL;
/**
 * @callback ResponseHandler
 * @param {Response} response
 * @returns {void}
 */
/**
 * @callback ExceptionHandler
 * @param {*} error
 * @returns {void}
 */
/**
 * @param {ResponseHandler} onResponse
 * @param {ExceptionHandler} onException
 * @returns
 */
function useRequest({
  onResponse = (response) => {},
  onException = (exception) => {},
  initialLoading = false,
}) {
  const [authInfo, setAuthInfo] = useContext(AuthContext);
  const [loading, setLoading] = useState(initialLoading);

  const request = useCallback(
    /**
     * @param {string} url
     * @param {object} options
     */
    (url, options) => {
      const headers = options?.headers || {};

      if (authInfo?.isAuthenticated) {
        headers.Authorization = `Bearer ${authInfo.token}`;
      }

      setLoading(true);
      fetch(`${API_URL}${url}`, {
        ...options,
        headers,
      })
        .then((response) => {
          if (response.status === 401 || response.status === 403) {
            setAuthInfo({
              isAuthenticated: false,
              token: null,
            });
          }

          setLoading(false);
          onResponse(response);
        })
        .catch((error) => {
          setLoading(false);
          onException(error);
        });
    },
    [authInfo, setAuthInfo, onResponse, onException]
  );

  return [request, loading];
}

export default useRequest;
