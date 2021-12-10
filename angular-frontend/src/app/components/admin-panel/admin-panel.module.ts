import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {AdminPanelComponent} from "./admin-panel.component";
import {CandidateService} from "../../services/candidate.service";

@NgModule({
  declarations: [
    AdminPanelComponent
  ],
  imports: [
    FormsModule,
    CommonModule
  ],
  exports: [
    AdminPanelComponent
  ],
  providers: [CandidateService]
})
export class AdminPanelModule { }
