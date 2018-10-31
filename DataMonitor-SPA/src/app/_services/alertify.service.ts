import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
declare let alertify: any;


@Injectable({
  providedIn: 'root'
})
export class AlertifyService {

constructor(private toaster: ToastrService) { }

  confirm(message: string, okCallback: () => any) {
    alertify.confirm(message, function(e) {
      if (e) {
        okCallback();
      } else {}
    });
  }

  sucess(message: string) {
    this.toaster.success(message);
  }

  error(message: string) {
    this.toaster.error(message, '', {
      timeOut: 3000
    });
  }

  warning(message: string) {
    this.toaster.warning(message);
  }

  message(message: string) {
    this.toaster.info(message);
  }
}

