const BASE_URL = 'https://api.com';

/**
 * @typedef {Object} RequestOptions
 * @property {string} method - HTTP 메서드 (GET, POST, PUT, PATCH, DELETE)
 * @property {Object.<string, string>} headers - 요청 헤더
 * @property {string} [body] - 요청 바디 (JSON 문자열)
 */

/**
 * API 요청을 보내는 함수
 * @template T
 * @param {string} method - HTTP 메서드 (GET, POST, PUT, PATCH, DELETE)
 * @param {string} url - API URL
 * @param {Object} [body] - 요청 바디 (옵션)
 * @returns {Promise<T>} - JSON 파싱된 응답 데이터
 */
const request = async (method, url, body) => {
  /** @type {RequestOptions} */
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  try {
    const response = await fetch(`${BASE_URL}${url}`, options);
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json();
  } catch (error) {
    console.error('API Request Error:', error);
    throw error;
  }
};

/**
 * API 모듈
 */
export const api = {
  /**
   * GET 요청
   * @template T
   * @param {string} url - API URL
   * @returns {Promise<T>}
   */
  get: (url) => request('GET', url),

  /**
   * POST 요청
   * @template T
   * @param {string} url - API URL
   * @param {Object} body - 요청 바디
   * @returns {Promise<T>}
   */
  post: (url, body) => request('POST', url, body),

  /**
   * PUT 요청
   * @template T
   * @param {string} url - API URL
   * @param {Object} body - 요청 바디
   * @returns {Promise<T>}
   */
  put: (url, body) => request('PUT', url, body),

  /**
   * PATCH 요청
   * @template T
   * @param {string} url - API URL
   * @param {Object} body - 요청 바디
   * @returns {Promise<T>}
   */
  patch: (url, body) => request('PATCH', url, body),

  /**
   * DELETE 요청
   * @template T
   * @param {string} url - API URL
   * @returns {Promise<T>}
   */
  delete: (url) => request('DELETE', url),
};
