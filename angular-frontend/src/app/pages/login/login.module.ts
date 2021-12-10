import { NgModule } from '@angular/core';
import { LoginComponent } from "./login.component";
import {LoginRoutingModule} from "./login-routing.module";
import {FormsModule} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    LoginComponent
  ],
  imports: [
    LoginRoutingModule,
    FormsModule,
    CommonModule,
  ],
  providers: [UserService],
  bootstrap: [LoginComponent]
})
export class LoginModule { }
