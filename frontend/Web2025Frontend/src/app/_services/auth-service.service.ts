import { Injectable } from '@angular/core';
import {Router} from "@angular/router";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private loggedInSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(this.isLoggedIn());

  constructor(private router: Router) { }

  // Poslužitelj za praćenje statusa logovanja
  public get loggedInStatus() {
    return this.loggedInSubject.asObservable();
  }

  public setToken(jwtToken: string) {
    localStorage.setItem('jwtToken', jwtToken);
    this.loggedInSubject.next(true); // Emituj true kada je korisnik ulogovan
  }

  public getToken(): string {
    // @ts-ignore
    return localStorage.getItem('jwtToken');
  }

  public isLoggedIn(): boolean {
    const token = this.getToken();
    return !!token; // Vraća true ako postoji token, inače false
  }

  // Brisanje tokena (odjava)
  public logOut() {
    localStorage.removeItem('jwtToken');
    this.loggedInSubject.next(false); // Emituj false kada korisnik odjavi
    this.router.navigate(['/login']);
  }
}
