/**
 * @param {string} url
 * @param {object} params
 */
function parseUrl(url, params = {}) {
  Object.keys(params).forEach((key) => {
    url = url.replace(`:${key}`, params[key]);
  });

  return url;
}

export default parseUrl;
