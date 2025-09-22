import { Injectable } from '@angular/core';
import {HttpClient, HttpClientModule, HttpHeaders, HttpParams} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  private API_URL = 'http://localhost:8080/user/getAllUsers'
  private API_URL_DELETE = 'http://localhost:8080/user/deleteUser'
  private API_URL_CREATE_USER = 'http://localhost:8080/user/create'
  private API_URL_UPDATE_USER = 'http://localhost:8080/user/updateUser'
  private API_URL_ERROR_MESSAGE = 'http://localhost:8080/error/messages'
  private apiUrl = 'http://localhost:8080/user/role'
  constructor(private httpClient : HttpClient) { }



  getUserRole(): Observable<string> {
    const token = localStorage.getItem('jwtToken'); // Uzimamo token iz localStorage-a
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}` // Dodajemo token u header
    });

    return this.httpClient.get<string>(this.apiUrl, { headers });
  }

  /// user methods
  getAllUsers(): Observable<any> {
    return this.httpClient.get(this.API_URL);
  }



  createUser(userData: any): Observable<any> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Dodaj token u Authorization header
      'Content-Type': 'application/json' // Dodaj Content-Type header
    });

    console.log('Poslati podaci:', userData); // Loguj podatke koji se šalju
    return this.httpClient.post(`${this.API_URL_CREATE_USER}`, userData, {
      headers,
      observe: 'response' // Koristi 'response' da bi imao pristup celokupnom odgovoru
    })
      .pipe(
        map(response => {
          // Proveri da li je odgovor uspešan (status 200)
          if (response.status === 200) {
            return { success: true, message: response.body };
          }
          throw new Error('Unexpected response status');
        }),
        catchError(error => {
          return throwError(error); // Obrada greške
        })
      );
  }



  updateUser(userData: any, id_user: number){
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Dodaj token u Authorization header
      'Content-Type': 'application/json' // Dodaj Content-Type header
    });

    return this.httpClient.put(`${this.API_URL_UPDATE_USER}?id_user=${id_user}`, userData, {
      headers,
      observe: 'response' // Koristi 'response' da bi imao pristup celokupnom odgovoru
    }).pipe(
      map(response => {
        if (response.status === 200) {
          return { success: true, message: response.body }; // Ekstraktuj telo odgovora
        }
        throw new Error('Unexpected response status');
      }),
      catchError(error => {
        return throwError(error);
      })
    );
  }


  deleteUser(userId: number): Observable<any> {

    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Dodaj token u Authorization header
    });

    return this.httpClient.delete(`${this.API_URL_DELETE}?id_user=${userId}`, { headers,
      responseType: 'text', observe: 'response' })
      .pipe(
        map(response => {
          if (response.status === 200) {
              return { success: true, message: response.body }; // Ekstraktuj telo odgovora
          }
          throw new Error('Unexpected response status');
        }),
        catchError(error => {
          return throwError(error);
        })
      );
  }


}
