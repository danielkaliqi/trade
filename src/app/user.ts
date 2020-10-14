import {Item} from './item';
import {Bid} from './bid';

export class User{
    id: string;
    username:string;
    email: string;
    items: Set<Item>;
    bids: Set<Bid>;
    roles: Set<any>;
}