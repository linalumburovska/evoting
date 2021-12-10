import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {BlockchainService} from "../../services/blockchain.service";
import {TransactionsTableComponent} from "./transactions-table.component";

@NgModule({
  declarations: [
    TransactionsTableComponent
  ],
  imports: [
    FormsModule,
    CommonModule
  ],
  exports: [
    TransactionsTableComponent
  ],
  providers: [BlockchainService]
})
export class TransactionsTableModule { }
