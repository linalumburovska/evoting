import { NgModule } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {CommonModule} from "@angular/common";
import {BlockchainViewerComponent} from "./blockchain-viewer.component";
import {BlockchainViewerRoutingModule} from "./blockchain-viewer-routing.module";
import {NavbarModule} from "../../components/navbar/navbar.module";
import {AdminPanelModule} from "../../components/admin-panel/admin-panel.module";
import {BlockViewModule} from "../../components/block-view/block-view.module";
import {BlockchainService} from "../../services/blockchain.service";
import {TransactionsTableModule} from "../../components/transactions-table/transactions-table.module";

@NgModule({
  declarations: [
    BlockchainViewerComponent
  ],
    imports: [
      BlockchainViewerRoutingModule,
      FormsModule,
      CommonModule,
      NavbarModule,
      AdminPanelModule,
      BlockViewModule,
      TransactionsTableModule
    ],
  providers: [UserService, BlockchainService],
  bootstrap: [BlockchainViewerComponent]
})
export class BlockchainViewerModule { }
