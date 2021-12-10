import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {BlockchainService} from "../../services/blockchain.service";
import {BlockViewComponent} from "./block-view.component";

@NgModule({
  declarations: [
    BlockViewComponent
  ],
  imports: [
    FormsModule,
    CommonModule
  ],
  exports: [
    BlockViewComponent
  ],
  providers: [BlockchainService]
})
export class BlockViewModule { }
