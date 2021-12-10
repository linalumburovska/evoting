import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {BlockchainViewerComponent} from "./blockchain-viewer.component";

const routes: Routes = [
  {
    path: '',
    component: BlockchainViewerComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BlockchainViewerRoutingModule { }
