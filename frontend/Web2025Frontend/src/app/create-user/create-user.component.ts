import { Component } from '@angular/core';
import {UserServiceService} from "../_services/user-service.service";

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent {

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


  constructor(private userService: UserServiceService) { }

  getUserData(): any {
    return {
      name: this.name,
      lastName: this.lastName,
      email: this.email,
      password: this.password,

        can_create: this.can_create,
        can_delete: this.can_delete,
        can_update: this.can_update,
        can_read: this.can_read,
        can_search_order: this.can_search_order,
        can_track_order: this.can_track_order,
        can_schedule_order: this.can_schedule_order,
        can_cancel_order: this.can_cancel_order

    };
  }

  message: { text: string, type: string } | null = null;

  onCreateUser(): void {
    const userData = this.getUserData(); // Funkcija koja preuzima podatke iz formular
    console.log('Poslati podaci:', userData);

    // Provera da li su svi obavezni podaci popunjeni
    if (!userData.name || !userData.lastName || !userData.email || !userData.password) {
      this.message = {
        text: 'Please fill out all required fields.',
        type: 'error'
      };
      return;
    }

    // Pozivanje servisa za kreiranje korisnika
    this.userService.createUser(userData).subscribe(
      (response) => {
        if (response.success) {
          this.message = {
            text: 'User created successfully!',
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
            text: 'You do not have permission to create a user.',
            type: 'error'
          };
        }
        else if (error.status === 409) {
          this.message = {
            text: 'User with email address already exist!.',
            type: 'error'
          };
        }
        else {
          this.message = {
            text: 'Failed to create user. Please try again.',
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
