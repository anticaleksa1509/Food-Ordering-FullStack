import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {UserServiceService} from "../_services/user-service.service";
import {OrderServiceService} from "../order-service.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {catchError, forkJoin, interval, of, Subscription, switchMap} from "rxjs";

@Component({
  selector: 'app-search-order',
  templateUrl: './search-order.component.html',
  styleUrls: ['./search-order.component.css']
})
export class SearchOrderComponent implements OnInit, OnDestroy {

  ordersAll: any[] = [];
  ordersUser: any[] = [];
  message: { text: string, type: string } | null = null;
  pollingSubscription!: Subscription;
  showOrders: boolean = false;
  isAdmin: boolean = false;
  isLoading: boolean = false; // Dodato - indikator učitavanja

  constructor(
      private orderService: OrderServiceService,
      private userService: UserServiceService,
  ) {}

  ngOnInit(): void {
    this.checkUserRole();

    this.pollingSubscription = interval(2000)
        .pipe(
            switchMap(() =>
                forkJoin({
                  allOrders: this.orderService.getAllOrders().pipe(catchError(() => of([]))),
                  userOrders: this.orderService.searchOrder().pipe(catchError(() => of({ success: false, message: [] })))
                })
            )
        )
        .subscribe((response) => {
          this.isLoading = false; // Kada podaci stignu, isključujemo loader
          this.updateOrders(response.allOrders, response.userOrders.message || []);
        });
  }

  checkUserRole(): void {
    this.userService.getUserRole().subscribe(role => {
      this.isAdmin = role.toLowerCase() === 'administrator';
    });
  }

  onSearch(): void {
    this.showOrders = true;
    this.isLoading = true; // Pokreće loading pre nego što podaci stignu
  }

  updateOrders(updatedAllOrders: any[], updatedUserOrders: any[]) {
    this.ordersAll = [];
    this.ordersUser = [];

    setTimeout(() => {
      this.ordersAll = [...updatedAllOrders]; // angular change detection
      this.ordersUser = [...updatedUserOrders];
    }, 0);
  }

  ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }
}
