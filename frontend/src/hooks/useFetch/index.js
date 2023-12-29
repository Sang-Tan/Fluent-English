import { useCallback, useEffect, useState } from "react";

import useRequest from "../useRequest";

function useFetch(url, { options = {}, initialData = null } = {}) {
  const [data, setData] = useState(initialData);
  const [error, setError] = useState(null);

  const handleResponse = useCallback(async (response) => {
    if (response.ok) {
      const data = await response.json();
      setData(data);
    } else {
      setError(await response);
    }
  }, []);

  const handleException = useCallback((exception) => {
    setError("Something went wrong");
    console.error("Error while fetching data", exception);
  }, []);

  const [request, loading] = useRequest({
    onResponse: handleResponse,
    onException: handleException,
    initialLoading: true,
  });

  const optionsString = JSON.stringify(options);

  useEffect(() => {
    request(url, JSON.parse(optionsString));
  }, [request, url, optionsString]);

  return {
    data,
    error,
    loading,
  };
}

export default useFetch;
