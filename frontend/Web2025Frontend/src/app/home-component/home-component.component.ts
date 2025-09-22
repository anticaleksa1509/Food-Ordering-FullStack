import { Component } from '@angular/core';

@Component({
  selector: 'app-home-component',
  templateUrl: './home-component.component.html',
  styleUrls: ['./home-component.component.css']
})
export class HomeComponentComponent {
  isLoggedIn(): boolean {
    return !!localStorage.getItem('jwtToken'); // Vraća true ako postoji token
  }
}
