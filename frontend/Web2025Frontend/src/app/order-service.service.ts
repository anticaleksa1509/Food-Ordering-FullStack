import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, catchError, map, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrderServiceService {

  private API_URL_CREATE_ORDER = 'http://localhost:8080/user/createOrder'
  private apiUrl = 'http://localhost:8080/user/searchForUser';
  private API_URL_ALL_ORDERS = 'http://localhost:8080/order/all'
  private apiUrlError = 'http://localhost:8080/api/errors';
  private apiUrlGeneral = 'http://localhost:8080/order';

  constructor(private httpClient : HttpClient) { }

  logError(userId: number, message: string): Observable<void> {
    return this.httpClient.post<void>(`${this.apiUrlError}?userId=${userId}&message=${message}`, {});
  }

  getUserErrors(userId: number): Observable<any[]> {
    return this.httpClient.get<any[]>(`${this.apiUrlError}/${userId}`);
  }

  private apiUrl1 = 'http://localhost:8080/schedule/createOrder';

  scheduleOrder1(localDateTime: string, orderData: any): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('jwtToken'));
    return this.httpClient.post(`${this.apiUrl1}?localDateTime=${encodeURIComponent(localDateTime)}`, orderData, { headers });
  }

  getUserOrders(): Observable<any[]> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.httpClient.get<any[]>(`${this.apiUrl}`, { headers });
  }

  cancelOrder(orderId: number): Observable<any> {
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.httpClient.put(`http://localhost:8080/order/cancelOrder?id_order=${orderId}`, {}, { headers });
  }

    getAllOrders(): Observable<any[]> {
        return this.httpClient.get<any[]>('http://localhost:8080/order/all').pipe(
            map(orders => orders.filter(order => order.status !== 'DELIVERED')) // Vraća samo aktivne porudžbine
        );
    }



    /*
  scheduleOrder(orderData: any, localDateTime: { active: boolean; dishes: any }): Observable<any> {
        const token = localStorage.getItem('jwtToken');
        const headers = new HttpHeaders({
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json'
        });

        //const url = `http://localhost:8080/schedule/createOrder?localDateTime=${encodeURIComponent(localDateTime)}`;

        return this.httpClient.post(url, orderData, { headers });
    }

     */
  searchOrder() : Observable<any>{
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Dodaj token u Authorization header
      'Content-Type': 'application/json' // Dodaj Content-Type header
    });

      return this.httpClient.get('http://localhost:8080/order/searchForUser',{headers,
      observe: 'response'})
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
  createOrder(orderDto:any): Observable<any>{
    const token = localStorage.getItem('jwtToken');
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`, // Dodaj token u Authorization header
      'Content-Type': 'application/json' // Dodaj Content-Type header
    });

    return this.httpClient.post(`${this.API_URL_CREATE_ORDER}`, orderDto, {
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
}
