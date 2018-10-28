import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../../_models/user';
import { AlertifyService } from '../../_services/alertify.service';
import { NgForm } from '@angular/forms';
import { UserService } from '../../_services/User.service';
import { AuthService } from '../../_services/auth.service';

@Component({
  selector: 'app-members-edit',
  templateUrl: './members-edit.component.html',
  styleUrls: ['./members-edit.component.css']
})
export class MembersEditComponent implements OnInit {
  user: User;
  @ViewChild('editForm') editForm: NgForm;
  // Candeactivate for whole window as Angular don't have routes control
  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any ) {
    if (this.editForm.dirty) {
      $event.returnValue = true;
    }
  }
  constructor(private route: ActivatedRoute, private alertify: AlertifyService,
          private userService: UserService, private authServcie: AuthService) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.user = data['user'];
      this.authServcie.loggedIn();
    });
  }

  updateUser() {
    this.userService.updateUser(this.authServcie.decodedToken.nameid, this.user).subscribe(next => {
      this.alertify.sucess('Profile Updated Successfully');
      this.editForm.reset(this.user);
    }, error => {
      this.alertify.error(error);
    });
  }
}
