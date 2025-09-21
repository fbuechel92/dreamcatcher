const BASE_URL =
  process.env.NODE_ENV === 'production'
    ? 'http://dream-catcher.me'
    : 'http://localhost:8080';

export { BASE_URL };