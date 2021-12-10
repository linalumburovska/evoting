import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {CommonModule} from "@angular/common";
import {NavbarComponent} from "./navbar.component";

@NgModule({
    declarations: [
        NavbarComponent
    ],
    imports: [
        FormsModule,
        CommonModule
    ],
    exports: [
        NavbarComponent
    ],
    providers: [UserService]
})
export class NavbarModule { }
