import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../user';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  
  content:any;
  id: String;
  user: User;

  constructor(private userService:UserService,private token: TokenStorageService,private route: ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
    this.user=this.token.getUser();
    console.log(this.user);
    this.userService.userAccess().subscribe(
      data => {
        this.content = data;
      },
      error => {
        console.log(error);
      }
    );
    
  }

}
