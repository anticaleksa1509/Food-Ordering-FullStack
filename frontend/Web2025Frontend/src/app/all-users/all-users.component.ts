import {Component, OnInit} from '@angular/core';
import {UserServiceService} from "../_services/user-service.service";

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit{

  users: any[] = [];

  constructor(private userService : UserServiceService) {
  }
  ngOnInit(): void {
    // Pozivanje servisa za preuzimanje korisnika
    this.userService.getAllUsers().subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }


}
