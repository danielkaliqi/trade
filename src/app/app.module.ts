import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { MyItemsComponent } from './myitems/myitems.component';
import { MybidsComponent } from './mybids/mybids.component';

import { authInterceptorProviders } from './_helpers/auth.interceptor';
import { MainComponent } from './main/main.component';
import { FilterPipe } from './filter.pipe';
import { ItemsComponent } from './items/items.component';
import { ItemDetailsComponent } from './item-details/item-details.component';
import { BidDetailsComponent } from './bid-details/bid-details.component';
import { AddBidComponent } from './add-bid/add-bid.component';
import { UpdateBidComponent } from './update-bid/update-bid.component';
import { UpdateItemComponent } from './update-item/update-item.component';
import { AddItemComponent } from './add-item/add-item.component';
import { AllUsersComponent } from './all-users/all-users.component';
import { AllItemsComponent } from './all-items/all-items.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    BoardAdminComponent,
    MyItemsComponent,
    MainComponent,
    MybidsComponent,
    FilterPipe,
    ItemsComponent,
    ItemDetailsComponent,
    BidDetailsComponent,
    AddBidComponent,
    UpdateBidComponent,
    UpdateItemComponent,
    AddItemComponent,
    AllUsersComponent,
    AllItemsComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    FlexLayoutModule,
    MatSlideToggleModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }