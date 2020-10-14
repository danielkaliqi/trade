import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  content: string;
  user:User;
  roles: string[] = [];

  constructor(private userService: UserService,private token:TokenStorageService,private router:Router) { }

  ngOnInit(): void {
    if(this.token.getUser()){
      this.roles=this.token.getUser().roles;
    if(this.roles.includes("ADMIN")){
    this.userService.adminAccess().subscribe(
      data => {
        this.content = data;
      },
      err => {
        console.log(err);
      }
    );}
    else{
      this.router.navigate(["/main"]);
    }
  }
  else{
    this.router.navigate(["/home"]);
  }
  }

}
