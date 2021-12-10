import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user.interface";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  user: User;

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit(): void {
    this.userService.findLoggedInUser().subscribe(user => {
        this.user = user;
    })
  }

  logout() {
    this.userService.updateLoggedOutUser(this.user.email).subscribe(() => {
        this.router.navigate(['/']);
      }
    )
  }

}
