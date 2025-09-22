import {Component, OnInit} from '@angular/core';
import {NgForm} from "@angular/forms";
import {LoginServiceService} from "../_services/login-service.service";
import {AuthServiceService} from "../_services/auth-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  email: string = '';
  password: string = '';
  message: { text: string, type: string } | null = null;
  constructor(private loginService: LoginServiceService,
              private authService: AuthServiceService,
              private router: Router) { }

  ngOnInit(): void { }

  login(loginForm: NgForm) {
    this.loginService.login(loginForm.value).subscribe(
      (response: any) => {
        console.log(response.token);
        this.authService.setToken(response.token);

        if (response.token) {
          this.router.navigate(['/allUsers']); // Preusmeri nakon logovanja
        }
      },
      (error) => {
        // Postavljanje greške koju smo dobili sa servera u komponentu
        this.message = error; // Poruka greške je sada objekat { text, type }
        console.log(error);
      }
    );
  }
}
