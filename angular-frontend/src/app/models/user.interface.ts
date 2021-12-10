export interface User {
  id?: number,
  firstName: string,
  lastName: string,
  email: string,
  password: string,
  confirmPassword: string,
  embg: string,
  adminOrUser: boolean,
  finishVoting: boolean,
  loggedIn: boolean
}
