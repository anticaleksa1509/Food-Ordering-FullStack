import {Component, OnInit} from '@angular/core';
import {UserServiceService} from "../_services/user-service.service";

@Component({
  selector: 'app-delete-user',
  templateUrl: './delete-user.component.html',
  styleUrls: ['./delete-user.component.css']
})
export class DeleteUserComponent implements OnInit{
  userId: string = '';  // Unos za ID korisnika
  message: { text: string, type: string } | null = null;

  constructor(private userService: UserServiceService) { }

  ngOnInit(): void { }

  onDeleteUser(): void {
    const userIdNumber = Number(this.userId);

    if (isNaN(userIdNumber) || userIdNumber <= 0) {
      this.message = {
        text: 'Please enter a valid user ID.',
        type: 'error'
      };
      return;
    }

    this.userService.deleteUser(userIdNumber).subscribe(
      (response) => {
        if (response.success) {
          this.message = {
            text: 'User deleted successfully!',
            type: 'success'
          };
          this.userId = ''; // Resetovanje input polja
        } else {
          this.message = {
            text: 'Unexpected response from the server.',
            type: 'error'
          };
        }
      },
      (error) => {
        if (error.status === 400) {
          this.message = {
            text: 'Invalid user ID. Please check the ID and try again.',
            type: 'error'
          };
        }else if (error.status === 403)
        {
          this.message = {
          text: 'You do not have permission for required action.',
          type: 'error'
        };

        } else {
          this.message = {
            text: 'Failed to delete user. Please try again.',
            type: 'error'
          };
        }
      }
    );
  }


}
