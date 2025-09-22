import { Component } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {OrderServiceService} from "../order-service.service";
import {UserServiceService} from "../_services/user-service.service";

@Component({
  selector: 'app-create-order',
  templateUrl: './create-order.component.html',
  styleUrls: ['./create-order.component.css']
})
export class CreateOrderComponent {


  constructor(private orderService: OrderServiceService,private userService: UserServiceService) {
  }
  active: Boolean = true;

  dishes: string[] = []; // Svi odabrani artikli

  orderDetails: any;


  drinks: string[] = [
    'Coca Cola',
    'Fanta Orange',
    'Sprite',
    'Schweppes Bitter Lemon',
    'Pepsi',
    'Jabuka sok',
    'Narandža sok',
    'Ananas sok',
    'Breskva sok',
    'Grožđe sok',
  ];

  pizzas: string[] = [
    'Pizza Margarita',
    'Pizza Capricciosa',
    'Pizza Pepperoni',
    'Pizza Vegeteriana',
    'Pizza Quattro Formaggi',
    'Pizza Diavola',
    'Pizza Hawaiiana',
    'Pizza Bianca',
    'Pizza Funghi',
    'Pizza BBQ Chicken',
  ];

  burgers: string[] = [
    'Classic Cheeseburger',
    'Bacon Cheeseburger',
    'Double Burger',
    'BBQ Burger',
    'Mushroom Swiss Burger',
    'Veggie Burger',
    'Chicken Burger',
    'Spicy Jalapeño Burger',
    'Fish Burger',
    'Blue Cheese Burger',
  ];

  desserts: string[] = [
    'Palačinke sa Nutellom',
    'Palačinke sa Plazmom',
    'Tiramisu',
    'Čokoladni lava kolač',
    'Cheese cake',
    'Baklava',
    'Mousse od čokolade',
    'Krempite',
    'Profiterole',
    'Voćni tart',
  ];


  selectedDishes: string[] = [];

  onDishSelected(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    const dish = checkbox.value;

    if (checkbox.checked) {
      // Dodaj u listu izabrana jela
      this.selectedDishes.push(dish);
    } else {
      // Ukloni iz liste izabranih jela
      this.selectedDishes = this.selectedDishes.filter(
        (selected) => selected !== dish
      );
    }
  }

  message: { text: string, type: string } | null = null;
  errorMessages: any[] = [];

  submitOrder(): void {

    if (this.dishes === null) {
      this.message = {
        text: 'Please select at least one dish to complete your order.',
        type: 'error'
      };
      return;
    }

    const orderDto = {
      active: true,
      dishes: this.selectedDishes,
    };

    this.orderService.createOrder(orderDto).subscribe(
      (response) => {
        if (response.success) {
          this.message = {
            text: 'Order created successfully!',
            type: 'success'
          };
          this.resetOrder();
        } else {
          this.message = {
            text: 'Unexpected response from the server.',
            type: 'error'
          };
        }
      },
      (error) => {
        if (error.status === 403) {
          this.message = {
            text: 'You do not have permission to create order.',
            type: 'error'

          };
        } else if (error.status === 400) {
          this.message = {
            text: 'Cannot create order because limit has been reached!',
            type: 'error',

          };
          this.orderService.logError(1,'Cannot create order because limit has been reached!')
        }
      }
    );
  }

  // Funkcija za resetovanje stanja porudžbine nakon uspešnog kreiranja
  private resetOrder() {
    this.dishes = [];  // Resetovanje liste jela
  }


}
