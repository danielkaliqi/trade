import { Component, OnInit } from '@angular/core';
import { Item } from '../item';
import { ItemsService } from '../items.service';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

  items: Item;
  itemsFound: boolean;
  active: boolean;

  constructor(private itemsService: ItemsService) { }

  ngOnInit(): void {
    this.itemsService.getAllItems().subscribe(
      data => {
        this.items = data;
          if (Object.keys(this.items).length === 0 || Object.values(this.items).some(v => v == null)) {
            this.itemsFound = false;
          }
          else {
            this.itemsFound = true;
          }
      }
    );
  }
}
