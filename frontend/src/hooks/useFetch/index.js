import { useEffect } from "react";

import useRequest from "../useRequest";

function useFetch(url, { options = null, initialData = null } = {}) {
  const { data, error, loading, request } = useRequest({
    initialData,
    initialLoading: true,
  });

  useEffect(() => {
    request(url, options ? options : {});
  }, [request, url, options]);

  return {
    data,
    error,
    loading,
  };
}

export default useFetch;
