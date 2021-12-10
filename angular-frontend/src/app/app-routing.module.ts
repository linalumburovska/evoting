import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('../app/pages/login/login.module').then(m => m.LoginModule)
  },
  {
    path: 'registration',
    loadChildren: () => import('../app/pages/registration/registration.module').then(m => m.RegistrationModule)
  },
  {
    path: 'blockchain',
    loadChildren: () => import('../app/pages/blockchain-viewer/blockchain-viewer.module').then(m => m.BlockchainViewerModule)
  },
  {
    path: 'new/transaction',
    loadChildren: () => import('../app/pages/create-transaction/create-transaction.module').then(m => m.CreateTransactionModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
