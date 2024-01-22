interface Authentication {
  accessToken: string;
}

type AuthenticationSetter = (authentication: Authentication | null) => void;

interface AuthenticationContext {
  auth: Authentication | null;
  setAuth: AuthenticationSetter;
}

export { Authentication, AuthenticationSetter, AuthenticationContext };
