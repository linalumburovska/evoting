import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {User} from "../models/user.interface";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly api: string;

  constructor(private http: HttpClient) {
    this.api = 'http://localhost:8080/users';
  }

  register(user: User) {
    return this.http.post(this.api + '/create', user);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.api);
  }

  findLoggedInUser(): Observable<User> {
    return this.http.get<User>(this.api + '/logged-in');
  }

  updateLoggedInUser(email: string) {
    return this.http.put(this.api + '/logged-in' , email);
  }

  updateLoggedOutUser(email: string) {
    return this.http.put(this.api + '/logged-out' , email);
  }

  finishVoting() {
    return this.http.put(this.api + '/finish-voting', {});
  }

}
