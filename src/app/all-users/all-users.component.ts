import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../user';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  users:Observable<User[]>;
 

  constructor(private userService:UserService) { }

  ngOnInit(): void {
    this.reloadData();
  }
  reloadData(){
    this.users=this.userService.getAllUsers();
  }
  deleteUser(id:string){
    if(confirm("Are you sure you want to delete this user?")){
    this.userService.deleteUserByAdmin(id).subscribe(
      data=>{
        console.log(data);
        this.reloadData();
      },
      error=>{
        console.log(error);
      }
    );
    }
  }

}
