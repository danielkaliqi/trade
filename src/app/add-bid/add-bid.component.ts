import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Bid } from '../bid';
import { BidsService } from '../bids.service';
import { Item } from '../item';
import { User } from '../user';
import { UserService } from '../_services/user.service';


@Component({
  selector: 'app-add-bid',
  templateUrl: './add-bid.component.html',
  styleUrls: ['./add-bid.component.css']
})
export class AddBidComponent implements OnInit {

  bid: Bid = new Bid();
  submitted = false;
  itemId: string;
  userId: string;
  user: User;
  items: Set<Item>;


  constructor(private bidsService: BidsService, private route: ActivatedRoute,private router:Router,
    private userService: UserService) { }

  ngOnInit(): void {
    this.userId = this.route.snapshot.paramMap.get("userId");
    this.userService.getUser(this.userId).subscribe(
      (user: User) => {
        this.items = user.items;
        this.bid.contact=user.email;
      });
  }

  addBid() {
    this.userId = this.route.snapshot.paramMap.get("userId");
    this.itemId = this.route.snapshot.paramMap.get("itemId");
    this.bid.status="pending";
    this.bidsService.addBid(this.itemId, this.userId, this.bid).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/myBids',this.userId]);
      },
      error => {
        console.log(error);
      }
    );
  }
  onSubmit() {
    this.addBid();
  }

}
