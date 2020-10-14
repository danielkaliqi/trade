import { Component, OnInit } from '@angular/core';
import { Bid } from '../bid';
import { BidsService } from '../bids.service';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../user';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-mybids',
  templateUrl: './mybids.component.html',
  styleUrls: ['./mybids.component.css']
})
export class MybidsComponent implements OnInit {

  userId: string;
  bids: Bid;
  bidsFound:boolean;
  user:User;
  id:string;

  constructor(private userService:UserService,private bidsService:BidsService,private route:ActivatedRoute) { }

  ngOnInit(): void {
    this.userId=this.route.snapshot.paramMap.get("userId");
    this.bidsService.getAllByUserId(this.userId).subscribe(
      data=>{
          this.bids=data;
          if(Object.keys(this.bids).values===null || Object.values(this.bids).some(v=>v !=null)===false){
            this.bidsFound=false;
            }
            else
            {
              this.bidsFound=true;
            }
      },
      error=>{
        console.log(error);
      }
    );
  }
  deleteBid(userId:string,id:string) {
    if(confirm("Are you sure you want to delete this bid?")){
    this.bidsService.deleteBid(userId,id).subscribe(
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

  reloadData(): void{
    location.reload();
  }

}
