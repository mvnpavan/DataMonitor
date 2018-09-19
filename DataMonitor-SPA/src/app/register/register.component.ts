import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @Output() cancelRegister = new EventEmitter();
  model: any = {};

  constructor(private authServcie: AuthService) { }

  ngOnInit() {
  }

  register() {
    this.authServcie.register(this.model)
      .subscribe(() => {
        console.log('Registration successful');
      }, error => {
        console.log('Registration failed!', error);
      });
  }

  cancel() {
    this.cancelRegister.emit(false);
    console.log('cancelled');
  }

}
