import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bid } from './bid';

const BID_URL='http://localhost:8080/trade/items/';
const MYBID_URL='http://localhost:8080/trade/myBids/'

@Injectable({
  providedIn: 'root'
})
export class BidsService {

  constructor(private http:HttpClient) { }

  addBid(itemId: String,userId: String,bid: Bid): Observable<Bid>{
    return this.http.post<Bid>(BID_URL+`${itemId}`+'/'+`${userId}`+'/addBid',bid);
  }
  getAllByUserId(userId: String): Observable<any>{
    return this.http.get(MYBID_URL+`${userId}`);
  }
  getBidByUserIdAndId(userId: String,id: String): Observable<any>{
    return this.http.get(MYBID_URL+`${userId}`+'/'+`${id}`);
  }
  updateBid(userId: String,id: String,value: any): Observable<Bid>{
    return this.http.put<Bid>(MYBID_URL+`${userId}`+'/'+`${id}`,value);
  }
  deleteBid(userId: String,id:String): Observable<any>{
    return this.http.delete(MYBID_URL+`${userId}`+'/'+`${id}`);
  }
}
