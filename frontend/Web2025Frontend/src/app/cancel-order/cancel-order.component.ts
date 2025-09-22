import { Component, OnInit } from '@angular/core';
import { OrderServiceService } from '../order-service.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-cancel-order',
  templateUrl: './cancel-order.component.html',
  styleUrls: ['./cancel-order.component.css']
})
export class CancelOrderComponent implements OnInit {

  orders: any[] = []; // Lista porudžbina
  message: { text: string, type: string } | null = null;

  constructor(private orderService: OrderServiceService) {}

  ngOnInit(): void {
    this.fetchOrders(); // Učitava porudžbine pri pokretanju stranice
  }

  fetchOrders(): void {
    this.orderService.getUserOrders().subscribe(
      (response) => {
        this.orders = response; // Čuva porudžbine u listi
      },
      (error: HttpErrorResponse) => {
        console.error('Error fetching orders:', error);
        this.message = { text: 'Error fetching orders. Please try again.', type: 'error' };
      }
    );
  }

  cancelOrder(orderId: number): void {
    this.orderService.cancelOrder(orderId).subscribe(
      (response) => {
        console.log('✅ Successfully canceled:', response);
        this.message = { text: response.message, type: 'success' };
        this.fetchOrders(); // Ponovo učitaj listu porudžbina
      },
      (error: HttpErrorResponse) => {
        console.error('❌ Error canceling order:', error);
        this.message = { text: error.error?.error || 'Error canceling order. Please try again.', type: 'error' };
      }
    );
  }
}
