import { RequestFunction } from "./types";

import { useContext, useCallback, useState } from "react";
import getEnv from "src/helpers/getEnv";
import AuthContext from "src/contexts/AuthContext";

const API_URL: string = getEnv("EXPO_PUBLIC_API_URL");

function useRequest() {
  const authContext = useContext(AuthContext);

  const request: RequestFunction = useCallback(
    async (url: string, options: RequestInit) => {
      const headers: any = options?.headers || {};
      const { auth, setAuth } = authContext;

      if (auth) {
        headers.Authorization = `Bearer ${auth.accessToken}`;
      }

      const resp = await fetch(`${API_URL}${url}`, {
        ...options,
        headers,
      });

      if (resp.status === 401 || resp.status === 403) {
        setAuth(null);
        throw new Error("Unauthorized");
      }

      return resp;
    },
    [authContext]
  );

  return request;
}

export default useRequest;
