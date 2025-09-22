import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponentComponent } from './home-component/home-component.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";
import { AllUsersComponent } from './all-users/all-users.component';
import { DeleteUserComponent } from './delete-user/delete-user.component';
import { CreateUserComponent } from './create-user/create-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { CreateOrderComponent } from './create-order/create-order.component';
import { SearchOrderComponent } from './search-order/search-order.component';
import { ScheduleOrderComponent } from './schedule-order/schedule-order.component';
import { ErrorMessageComponent } from './error-message/error-message.component';
import {OrderServiceService} from "./order-service.service";
import { CancelOrderComponent } from './cancel-order/cancel-order.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponentComponent,
    HeaderComponent,
    LoginComponent,
    AllUsersComponent,
    DeleteUserComponent,
    CreateUserComponent,
    UpdateUserComponent,
    CreateOrderComponent,
    SearchOrderComponent,
    ScheduleOrderComponent,
    ErrorMessageComponent,
    CancelOrderComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        RouterModule,
        ReactiveFormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]//sadrzi sve komponente koje treba da se prikazu u aplikaciji
})
export class AppModule { }
