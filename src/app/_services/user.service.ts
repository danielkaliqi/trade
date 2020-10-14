import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../user';

const API_URL = 'http://localhost:8080/trade/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  allAccess(): Observable<any> {
    return this.http.get(API_URL + 'home', { responseType: 'text' });
  }
  userAccess(): Observable<any>{
    return this.http.get(API_URL + 'main');
  }
  adminAccess(): Observable<any> {
    return this.http.get(API_URL + 'admin', { responseType: 'text' });
  }
  getUser(userId:string): Observable<User>{
    return this.http.get<User>(API_URL+'getUser/'+userId);
  }
  getAllUsers():Observable<any>{
    return this.http.get(API_URL+'admin/allUsers');
  }
  getAllItems():Observable<any>{
    return this.http.get(API_URL+'admin/allItems');
  }
  deleteUserByAdmin(id:string):Observable<any>{
    return this.http.delete(API_URL+'admin/'+`${id}`+'/deleteUser');
  }
  deleteItemByAdmin(itemId:string):Observable<any>{
    return this.http.delete(API_URL+'admin/'+`${itemId}`+'/deleteItem');
  }
}
