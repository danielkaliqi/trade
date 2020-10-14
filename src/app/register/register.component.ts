import { Component, Input, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../_services/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {};
  isSignUpFailed = false;

  constructor(private authService: AuthService,private router:Router) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.authService.register(this.form).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/login']);
      },
      err => {
        console.log(err);
        this.isSignUpFailed = true;
      }
    );
  }
}