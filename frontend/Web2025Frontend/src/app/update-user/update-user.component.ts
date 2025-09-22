import { Component } from '@angular/core';
import {UserServiceService} from "../_services/user-service.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent {

  id_user: string = '';
  name: string = '';
  lastName: string = '';
  email: string = '';
  password: string = '';
  can_delete: Boolean = false;
  can_update: Boolean = false;
  can_read: Boolean = false;
  can_create: Boolean = false;
  can_search_order: Boolean = false;
  can_cancel_order: Boolean = false;
  can_track_order: Boolean = false;
  can_schedule_order: Boolean = false;

  message: { text: string, type: string } | null = null;
  constructor(private userService: UserServiceService) { }

  getUserData(): any {
    const userData: any = {};

    // Dodajemo samo polja koja su izmenjena
    if (this.name.trim()) userData.name = this.name;
    if (this.lastName.trim()) userData.lastName = this.lastName;
    if (this.email.trim()) userData.email = this.email;
    if (this.password.trim()) userData.password = this.password;

    // Dodajemo samo ako su permissions promenjeni
    if (this.can_create) userData.can_create = this.can_create;
    if (this.can_delete) userData.can_delete = this.can_delete;
    if (this.can_update) userData.can_update = this.can_update;
    if (this.can_read) userData.can_read = this.can_read;
    if (this.can_search_order) userData.can_search_order = this.can_search_order;
    if (this.can_track_order) userData.can_track_order = this.can_track_order;
    if (this.can_schedule_order) userData.can_schedule_order = this.can_schedule_order;
    if (this.can_cancel_order) userData.can_cancel_order = this.can_cancel_order;

    return userData;
  }

  onUpdateUser() {
    const userData = this.getUserData();
    if(!this.id_user){
      this.message = {
        text: 'Please fill out User Id field.',
        type: 'error'
      };
      return;
    }
    const userIdNumber = Number(this.id_user);
    this.userService.updateUser(userData,userIdNumber).subscribe(
      (response) => {
        if (response.success) {
          this.message = {
            text: 'User updated successfully!',
            type: 'success'
          };
          this.resetForm();
        } else {
          this.message = {
            text: 'Unexpected response from the server.',
            type: 'error'
          };
        }
      },
      (error) => {
        // Provera greške u zavisnosti od statusa
        if (error.status === 400) {
          this.message = {
            text: 'Invalid user data. Please check the input values.',
            type: 'error'
          };
        } else if (error.status === 403) {
          this.message = {
            text: 'You do not have permission to update a user.',
            type: 'error'
          };
        }
        else if (error.status === 409) {
          this.message = {
            text: 'User with email address already exist!.',
            type: 'error'
          };
        }else if (error.status === 404) {
          this.message = {
            text: 'User with that ID does not exist!.',
            type: 'error'
          };
        }
        else {
          this.message = {
            text: 'Failed to update user. Please try again.',
            type: 'error'
          };
        }
      }
    );
  }

  private resetForm() {
    this.name = '';
    this.lastName = '';
    this.email = '';
    this.password = '';
    this.can_create = false;
    this.can_delete = false;
    this.can_update = false;
    this.can_read = false;
    this.can_cancel_order = false;
    this.can_track_order = false;
    this.can_schedule_order = false;
    this.can_search_order = false;
  }

}
