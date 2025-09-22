import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean {
    const token = localStorage.getItem('jwtToken');

    if (token) {
      // Ako postoji token, korisnik može pristupiti stranici
      return true;
    } else {
      // Ako nema tokena, preusmeri na login stranicu
      this.router.navigate(['/login']);
      return false;
    }
  }
}
