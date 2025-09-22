import {Component, OnInit} from '@angular/core';
import {OrderServiceService} from "../order-service.service";

@Component({
  selector: 'app-error-message',
  templateUrl: './error-message.component.html',
  styleUrls: ['./error-message.component.css']
})
export class ErrorMessageComponent implements OnInit{
  errorMessages: any[] = [];
  userId: number = 1; // Ovo treba dinamički dohvatiti iz autentifikacije

  constructor(private errorMessageService: OrderServiceService) {}

  ngOnInit(): void {
    this.loadErrors();
  }

  loadErrors(): void {
    this.errorMessageService.getUserErrors(this.userId).subscribe((errors) => {
      this.errorMessages = errors;
    });
  }
}
