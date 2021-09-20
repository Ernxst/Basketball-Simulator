import jwtDecode, { JwtPayload } from "jwt-decode";

const TOKEN_KEY_NAME = "token";

const decodeToken = (token: string) => {
  if (token) {
    return jwtDecode<JwtPayload>(token);
  }
  return {};
};

export const getTokenFromStorage = (): string | null => {
  return localStorage.getItem(TOKEN_KEY_NAME);
};

export const setJwtToken = (token: string): void => {
  localStorage.setItem(TOKEN_KEY_NAME, token);
};

export const removeJwtToken = (): void => {
  localStorage.removeItem(TOKEN_KEY_NAME);
};

export const getUsernameFromToken = (token: string): string | undefined => {
  const decoded = decodeToken(token);
  return decoded.sub;
};

export const getUsernameFromStorage = (): string | undefined => {
  const token = getTokenFromStorage();
  if (token) return getUsernameFromToken(token);
  return undefined;
};

export const isExpired = (token: string): boolean => {
  const decoded = decodeToken(token);
  if (decoded.exp) return decoded.exp < Date.now();
  return true;
};
