import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, pipe, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
  export class LoginServiceService {

    PATH_OF_API = "http://localhost:8080/login";
    requestHeader = new HttpHeaders(
      {"No-Auth":"True"}
    );
    constructor(private httpClient : HttpClient) { }

  public login(loginData: any) {
    return this.httpClient.post(this.PATH_OF_API, loginData, { headers: this.requestHeader })
      .pipe(
        catchError(error => {
          // Ako je status 400, prosledjujemo grešku sa odgovarajućom porukom
          if (error.status === 400) {
            return throwError({ text: 'Invalid credentials! Please check your email and password.', type: 'error' });
          }
          // Obrada ostalih grešaka
          return throwError({ text: 'An unexpected error occurred. Please try again later.', type: 'error' });
        })
      );
  }
  }
