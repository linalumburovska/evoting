import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.interface";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email: any;
  password: any;
  userDoesNotExist: boolean = false;
  users: User[];

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(users => this.users = users);
  }

  login() {
    const findUser =this.users.find((el: any) => el.email === this.email);
    if(findUser != undefined) {
      this.userService.updateLoggedInUser(findUser.email).subscribe(() => {
        this.userService.findLoggedInUser().subscribe();
        this.router.navigate(['/blockchain']);
      })
    } else {
      this.userDoesNotExist = true;
    }
  }


}
