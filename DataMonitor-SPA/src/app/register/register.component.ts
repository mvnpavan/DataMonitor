import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { AlertifyService } from '../_services/alertify.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @Output() cancelRegister = new EventEmitter();
  model: any = {};

  constructor(private authServcie: AuthService, private alertifyService: AlertifyService) { }

  ngOnInit() {
  }

  register() {
    this.authServcie.register(this.model)
      .subscribe(() => {
        this.alertifyService.sucess('Registration successful');
      }, error => {
        this.alertifyService.error(error);
      });
  }

  cancel() {
    this.cancelRegister.emit(false);
    this.alertifyService.message('Cancelled successfully');
  }

}
