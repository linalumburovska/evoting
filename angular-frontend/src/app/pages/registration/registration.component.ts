import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.interface";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  firstName: any;
  lastName: any;
  email: any;
  password: any;
  confirmPassword: any;
  embg: any;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  register() {
    const newUser: User = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      password: this.password,
      confirmPassword: this.confirmPassword,
      embg: this.embg,
      adminOrUser: false,
      finishVoting: false,
      loggedIn: false
    }
    this.userService.register(newUser).subscribe(() => {
      this.router.navigate(['/']);
    })

  }

}
