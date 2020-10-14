import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable,BehaviorSubject, Subject} from 'rxjs';
import { Item } from './item';
import { Bid } from './bid';

const ITEM_URL='http://localhost:8080/trade/items/'
const MYITEM_URL='http://localhost:8080/trade/myItems/'

@Injectable({
  providedIn: 'root'
})
export class ItemsService {

  constructor(private http:HttpClient) { }

  getAllItems(): Observable<any>{
    return this.http.get(ITEM_URL+'all');
  }
  getItemById(id: String): Observable<any>{
    return this.http.get(ITEM_URL+`${id}`);
  }
  getAllBidsByItemId(id: String): Observable<any>{
    return this.http.get(ITEM_URL+`${id}`+'/bids');
  }
  getAllItemsByUserId(userId: String): Observable<any>{
    return this.http.get(MYITEM_URL+`${userId}`);
  }
  getItemByUserId(userId: String,id: String): Observable<any>{
    return this.http.get(MYITEM_URL+`${userId}`+'/'+`${id}`);
  }
  addItem(item:Item,userId: String): Observable<Item>{
    return this.http.post<Item>(MYITEM_URL+`${userId}`+'/addItem',item);
  }
  updateItem(userId: String,id: String, value: any): Observable<any>{
    return this.http.put(MYITEM_URL+`${userId}`+'/'+`${id}`+'/update',value);
  }
  deleteItem(userId: String,id: String): Observable<any>{
    return this.http.delete(MYITEM_URL+`${userId}`+'/'+`${id}`,{ responseType: 'text' });
  }
}
