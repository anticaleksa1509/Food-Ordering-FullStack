import {Component, OnInit} from '@angular/core';
import {AuthServiceService} from "../_services/auth-service.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  isUserLoggedIn: boolean = false;

  constructor(private authService: AuthServiceService) { }

  ngOnInit() {
    this.authService.loggedInStatus.subscribe(status => {
      this.isUserLoggedIn = status;  // Ažuriraj status kada se menja
    });
  }

  logOutMethod() {
    this.authService.logOut();
  }

  deleteUser() {

  }
}
