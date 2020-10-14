import { Item } from '../item';
import { Component, OnInit } from '@angular/core';
import { ItemsService } from '../items.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {

  id: String;
  item: Item;
  user:any;

  constructor(private itemsService: ItemsService,private route: ActivatedRoute,private router:Router,private token:TokenStorageService) { }

  ngOnInit(): void {
    this.user=this.token.getUser();
    this.id = this.route.snapshot.paramMap.get('id');
    this.itemsService.getItemById(this.id).subscribe(
      data => {
        this.item=data;
      });
  }

}
