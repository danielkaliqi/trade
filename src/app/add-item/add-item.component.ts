import { JsonPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Item } from '../item';
import { ItemsService } from '../items.service';

@Component({
  selector: 'app-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css']
})
export class AddItemComponent implements OnInit {

  item: Item = new Item();
  submitted = false;
  userId: string;
  selectedFile: File;
  url:any;
  addedItem:boolean=false;

  constructor(private itemService: ItemsService, private route: ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
  }
  addItem() {
    this.userId = this.route.snapshot.paramMap.get("userId");
    this.item.image=this.url;
    this.itemService.addItem(this.item, this.userId).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/myItems',this.userId]);
      },
      error => {
        console.log(error);
      }
    );
    this.item = new Item();
  }

  onSubmit() {
    this.addItem();
  }
  updateFile(event:Event) {
    this.selectedFile = (event.target as HTMLInputElement).files[0];
    if(this.selectedFile.size>2097152){
      alert("File is too big!");
    }
    else{
    var reader=new FileReader();
    reader.readAsDataURL(this.selectedFile);
    reader.onload=(event)=>{
      this.url=reader.result;
    }
    this.addedItem=true;
   }
  }
}