import { Component, OnInit } from '@angular/core';
import { User } from '../../_models/user';
import { Route, ActivatedRoute } from '@angular/router';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryAnimation } from 'ngx-gallery';

@Component({
  selector: 'app-memeberlist',
  templateUrl: './memeberlist.component.html',
  styleUrls: ['./memeberlist.component.css']
})
export class MemeberlistComponent implements OnInit {
  users: User[];

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
        this.users = data['users'];
    });
  }
}
