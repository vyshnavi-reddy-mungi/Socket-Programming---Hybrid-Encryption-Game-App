import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserRegistration } from '../models/UserRegistrationModel';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  userRegistration: UserRegistration = {};

  errorMessage: string = '';

  constructor(private apiService: ApiService, private router: Router) {

  }

  onSubmit() {
    this.apiService.postService(this.apiService.apiUrls.register, this.userRegistration)
    .toPromise().then(res => {
      if (res?.status === true) {
        this.router.navigateByUrl("/login");
      } else {
        this.errorMessage = res?.responseMessage ? res.responseMessage : '';
      }
    },
    error => {

    });
  }

}
