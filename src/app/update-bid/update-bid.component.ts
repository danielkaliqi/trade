import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Bid } from '../bid';
import { BidsService } from '../bids.service';
import { Item } from '../item';

@Component({
  selector: 'app-update-bid',
  templateUrl: './update-bid.component.html',
  styleUrls: ['./update-bid.component.css']
})
export class UpdateBidComponent implements OnInit {

  userId:string;
  id: string;
  bid: Bid;
  isSubmitted:boolean;
  item: Item;

  constructor(private bidService:BidsService,private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.bid=new Bid();
    this.id=this.route.snapshot.paramMap.get("id");
    this.userId=this.route.snapshot.paramMap.get("userId");
    
    this.bidService.getBidByUserIdAndId(this.userId,this.id).subscribe(
      data=>{
        console.log(data);
        this.bid=data;
      },
      error=>{
        console.log(error);
      }
    );
  }
  updateBid(){
    this.id=this.route.snapshot.paramMap.get("id");
    this.userId=this.route.snapshot.paramMap.get("userId");
    this.bidService.updateBid(this.userId,this.id,this.bid).subscribe(
      data=>{
        console.log(data);
        this.isSubmitted=true;
      },
      error=>{
        console.log(error);
      }
    );
  }
  onSubmit(){
    this.updateBid();
  }
}
