import { Bid } from '../bid';
import { Component, OnInit } from '@angular/core';
import { ItemsService } from '../items.service';
import { Router, ActivatedRoute } from '@angular/router';
import { getNumberOfCurrencyDigits, JsonPipe, NgForOf } from '@angular/common';
import { BidsService } from '../bids.service';
import { Item } from '../item';

@Component({
  selector: 'app-bid-details',
  templateUrl: './bid-details.component.html',
  styleUrls: ['./bid-details.component.css']
})
export class BidDetailsComponent implements OnInit {

  id: String;
  bids: Bid;
  bidi:Bid;
  item:Item;
  errorMessage: 'error'
  bidFound:boolean;
  userId:string;
  myItem:boolean;
  acceptedBid:Bid;
  accepted:boolean;

  constructor(private bidsService:BidsService,private itemsService: ItemsService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {

    this.id = this.route.snapshot.paramMap.get('id');
    this.userId=this.route.snapshot.paramMap.get('userId');
    if(this.userId===null){
      this.myItem=false;
    }
    else{
      this.myItem=true;
    }
    this.itemsService.getItemById(this.id).subscribe(
      data=>{
        this.item=data;
      },
      error=>{
        console.log(error);
      }
      );
    this.itemsService.getAllBidsByItemId(this.id).subscribe(
      data => {
        this.bids = data;
        if(Object.keys(this.bids).length===0 || Object.values(this.bids).some(v=>v==null)){
        this.bidFound=false;
        }
        else
        {
          this.bidFound=true;
        }
      },
      err => {
        this.errorMessage = err.error.message;
      }
    );
    
  }
   acceptDeal(userId:string,bidId:string){
    if(confirm("Are you sure you want to accept this deal?")){
      this.bidsService.getBidByUserIdAndId(userId,bidId).subscribe(
        data=>{
          this.acceptedBid=data;
          this.acceptedBid.status="accepted";
          console.log(this.acceptedBid);
            this.bidsService.updateBid(userId,bidId,this.acceptedBid).subscribe(
              data=>{
                console.log(data);
                this.reload();
              }
            ); 
            this.item.active=false;
            this.itemsService.updateItem(userId,this.id,this.item).subscribe(
              data=>{
                console.log(data);
              },
              error=>{
                console.log(error);
              }
            );
        },
        error=>{
        console.error(error);
        }
      );
    }
}
  declineDeal(userId:string,bidId:string){
    this.bidsService.getBidByUserIdAndId(userId,bidId).subscribe(
      data=>{
        this.acceptedBid=data;
        this.acceptedBid.status="declined";
        console.log(this.acceptedBid);
        if(confirm("Are you sure you want to decline this deal?")){
          this.bidsService.updateBid(userId,bidId,this.acceptedBid).subscribe(
            data=>{
              console.log(data);
              this.reload();
            }
          ); 
        }
      },
      error=>{
      console.error(error);
      }
    );
  }
  reload(){
    location.reload();
  }
}
