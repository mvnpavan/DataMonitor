import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { AlertifyService } from '../_services/alertify.service';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { BsDatepickerConfig } from 'ngx-bootstrap';
import { User } from '../_models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @Output() cancelRegister = new EventEmitter();
  user: User;
  registerForm: FormGroup;
  bsConfig: Partial<BsDatepickerConfig>; // Partial make all values optional in type of onject

  constructor(private authServcie: AuthService, private alertifyService: AlertifyService, 
    private fb: FormBuilder, private router: Router) { }

  ngOnInit() {
    this.bsConfig = {
      containerClass: 'theme-red'
    };
    this.createRegisterForm();
  }

  createRegisterForm() {
    this.registerForm = this.fb.group({
      gender: ['male'],
      username: ['', Validators.required],
      knownAs: ['', Validators.required],
      dateOfBirth: [null, Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(8)]],
      confirmPassword: ['', Validators.required]
    }, {validator: this.passwordMatchValidator});
  }

  passwordMatchValidator(g: FormGroup) {
    return g.get('password').value === g.get('confirmPassword').value ? null : {'mismatch': true};
  }

  register() {
    if (this.registerForm.valid) {
      this.user = Object.assign({}, this.registerForm.value);
      this.authServcie.register(this.user).subscribe(() => {
        this.alertifyService.sucess('Registration Sucessful');
      }, error => {
        this.alertifyService.error(error);
      }, () => {
        this.authServcie.login(this.user).subscribe(() => {
          this.router.navigate(['/members']);
        });
      });
    }
    // this.authServcie.register(this.model)
    //   .subscribe(() => {
    //     this.alertifyService.sucess('Registration successful');
    //   }, error => {
    //     this.alertifyService.error(error);
    //   });
    console.log(this.registerForm.value);
  }

  cancel() {
    this.cancelRegister.emit(false);
    this.alertifyService.message('Cancelled successfully');
  }

}
