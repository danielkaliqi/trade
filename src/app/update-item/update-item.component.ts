import { Component, OnInit } from '@angular/core';
import { MatSlideToggle, MatSlideToggleChange } from '@angular/material/slide-toggle';
import { ActivatedRoute, Router } from '@angular/router';
import { Item } from '../item';
import { ItemsService } from '../items.service';

@Component({
  selector: 'app-update-item',
  templateUrl: './update-item.component.html',
  styleUrls: ['./update-item.component.css']
})
export class UpdateItemComponent implements OnInit {

  userId:string;
  id: string;
  isSubmitted:boolean;
  item: Item;
  selectedFile:File;
  url:any;
  checked:any;

  constructor(private itemService:ItemsService,private route:ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
    this.id=this.route.snapshot.paramMap.get("id");
    this.userId=this.route.snapshot.paramMap.get("userId");
    
    this.itemService.getItemByUserId(this.userId,this.id).subscribe(
      data=>{
        console.log(data);
        this.item=data;
        this.url=this.item.image;
      },
      error=>{
        console.log(error);
      }
    );
  }
  updateBid(){
    this.item.image=this.url;
    this.item.active=this.checked;
    this.itemService.updateItem(this.userId,this.id,this.item).subscribe(
      data=>{
        console.log(data);
        this.router.navigate(['/myItems',this.userId]);
      },
      error=>{
        console.log(error);
      }
    );
  }
  onSubmit(){
    this.updateBid();
  }
  updateFile(event:Event) {
    this.selectedFile = (event.target as HTMLInputElement).files[0];
    var reader=new FileReader();
    reader.readAsDataURL(this.selectedFile);
    reader.onload=(event)=>{
      this.url=reader.result;
    }
   }
   toggle(event:MatSlideToggleChange){
      this.checked=event.checked;
   }
}
