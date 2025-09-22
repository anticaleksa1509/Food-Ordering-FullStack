import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponentComponent} from "./home-component/home-component.component";
import {LoginComponent} from "./login/login.component";
import {AllUsersComponent} from "./all-users/all-users.component";
import {DeleteUserComponent} from "./delete-user/delete-user.component";
import {CreateUserComponent} from "./create-user/create-user.component";
import {UpdateUserComponent} from "./update-user/update-user.component";
import {CreateOrderComponent} from "./create-order/create-order.component";
import {SearchOrderComponent} from "./search-order/search-order.component";
import {ScheduleOrderComponent} from "./schedule-order/schedule-order.component";
import {ErrorMessageComponent} from "./error-message/error-message.component";
import {CancelOrderComponent} from "./cancel-order/cancel-order.component";

const routes: Routes = [
  {path:'home',component:HomeComponentComponent},
  {path:'login',component:LoginComponent},
  //{path:'forbidden',component:ForbiddenComponent},
  {path: 'allUsers',component:AllUsersComponent},
  {path: 'deleteUser',component:DeleteUserComponent},
  {path: 'createUser', component: CreateUserComponent},
  {path: 'updateUser', component: UpdateUserComponent},
  {path: 'createOrder', component: CreateOrderComponent},
  {path: 'searchOrder',component: SearchOrderComponent},
  {path: 'scheduleOrder',component: ScheduleOrderComponent},
  {path: 'cancelOrder',component: CancelOrderComponent},
  {path: '**', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
