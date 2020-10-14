import { Component, OnInit } from '@angular/core';
import { Item } from '../item';
import { ItemsService } from '../items.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-myitems',
  templateUrl: './myitems.component.html',
  styleUrls: ['./myitems.component.css']
})
export class MyItemsComponent implements OnInit {

  userId: string;
  Items: Item;
  itemsFound: boolean;
  id:string;

  constructor(private itemsService:ItemsService,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.userId=this.route.snapshot.paramMap.get('userId');
    this.itemsService.getAllItemsByUserId(this.userId).subscribe(
      data =>
      {
        this.Items=data;
        if(Object.keys(this.Items).length===0 || Object.values(this.Items).some(v=>v==null)){
          this.itemsFound=false;
          }
          else
          {
            this.itemsFound=true;
          }
      }
    );
  }
  deleteItem(userId:string,id:string){
    if(confirm("Are you sure you want to delete this item?")){
    this.itemsService.deleteItem(userId,id).subscribe(
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
  reloadData():void{
    location.reload();
  }
}
