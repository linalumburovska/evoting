import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {CreateTransactionComponent} from "./create-transaction.component";
import {CreateTransactionRoutingModule} from "./create-transaction-routing.module";
import {NavbarModule} from "../../components/navbar/navbar.module";
import {TransactionsTableModule} from "../../components/transactions-table/transactions-table.module";
import {CommonModule} from "@angular/common";

@NgModule({
  declarations: [
    CreateTransactionComponent
  ],
  imports: [
    CreateTransactionRoutingModule,
    FormsModule,
    NavbarModule,
    TransactionsTableModule,
    CommonModule
  ],
  providers: [UserService],
  bootstrap: [CreateTransactionComponent]
})
export class CreateTransactionModule { }
