// schedule-order.component.ts
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {OrderServiceService} from "../order-service.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-schedule-order',
  templateUrl: './schedule-order.component.html',
  styleUrls: ['./schedule-order.component.css']
})
export class ScheduleOrderComponent {
  scheduleForm: FormGroup;

  constructor(private fb: FormBuilder, private orderService: OrderServiceService) {
    this.scheduleForm = this.fb.group({
      localDateTime: ['', Validators.required],
      dishes: ['', Validators.required]
    });
  }

    scheduleOrder() {
        if (this.scheduleForm.valid) {
            const orderData = {
                active: true,
                dishes: this.scheduleForm.value.dishes.split(',').map((dish: string) => dish.trim())
            };
            const scheduledTime = this.scheduleForm.value.localDateTime;

            this.orderService.scheduleOrder1(scheduledTime, orderData).subscribe({
                next: response => {
                    alert('Order scheduled successfully!');
                },
                error: (error: HttpErrorResponse) => {
                    if (error.status === 403) {
                        alert('You do not have permission to schedule an order.');
                    } else {
                        alert('Failed to schedule order.');
                    }
                    console.error('Error response:', error);
                }
            });
        }
    }
}
