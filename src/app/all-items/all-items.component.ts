import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from '../item';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-all-items',
  templateUrl: './all-items.component.html',
  styleUrls: ['./all-items.component.css']
})
export class AllItemsComponent implements OnInit {

  items:Observable<Item[]>;
 

  constructor(private userService:UserService) { }

  ngOnInit(): void {
    this.reloadData();
  }
  reloadData(){
    this.items=this.userService.getAllItems();
  }
  deleteItem(itemId:string){
    if(confirm("Are you sure you want to delete this item?")){
    this.userService.deleteItemByAdmin(itemId).subscribe(
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
