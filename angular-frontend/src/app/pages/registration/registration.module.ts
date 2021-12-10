import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {RegistrationComponent} from "./registration.component";
import {RegistrationRoutingModule} from "./registration-routing.module";

@NgModule({
  declarations: [
    RegistrationComponent
  ],
  imports: [
    RegistrationRoutingModule,
    FormsModule
  ],
  providers: [UserService],
  bootstrap: [RegistrationComponent]
})
export class RegistrationModule { }
