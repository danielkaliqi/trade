import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { MyItemsComponent } from './myitems/myitems.component';
import { MybidsComponent } from './mybids/mybids.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { MainComponent } from './main/main.component';
import { ItemsComponent} from './items/items.component';
import { ItemDetailsComponent } from './item-details/item-details.component';
import { BidDetailsComponent } from './bid-details/bid-details.component';
import { AddBidComponent } from './add-bid/add-bid.component';
import { UpdateBidComponent } from './update-bid/update-bid.component';
import { UpdateItemComponent } from './update-item/update-item.component';
import { AddItemComponent } from './add-item/add-item.component';
import { AllUsersComponent } from './all-users/all-users.component';
import { AllItemsComponent } from './all-items/all-items.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'items/all', component: ItemsComponent },
  { path: 'items/:id', component: ItemDetailsComponent },
  { path: 'items/:id/bids', component: BidDetailsComponent },
  { path: 'items/:itemId/:userId/addBid', component: AddBidComponent },
  { path: 'myItems/:userId', component: MyItemsComponent },
  { path: 'myItems/:userId/:id/update', component: UpdateItemComponent },
  { path: 'myItems/:userId/:id/bids', component: BidDetailsComponent },
  { path: 'myItems/:userId/:id/:bidId', component: BidDetailsComponent },
  { path: 'myItems/:userId/addItem', component: AddItemComponent },
  { path: 'myBids/:userId', component: MybidsComponent},
  { path: 'myBids/:userId/:id', component: UpdateBidComponent},
  { path: 'admin', component: BoardAdminComponent },
  { path: 'admin/allUsers', component: AllUsersComponent },
  { path: 'admin/allItems', component: AllItemsComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'main', component: MainComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }