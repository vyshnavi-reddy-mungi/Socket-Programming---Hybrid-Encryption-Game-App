import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ForgetPassword } from '../models/ForgetPassword';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-forget-password',
  templateUrl: './forget-password.component.html',
  styleUrls: ['./forget-password.component.css']
})
export class ForgetPasswordComponent {
  forgetPass: ForgetPassword = {};
  errorMessage: string = '';

  constructor(private apiService: ApiService, private router: Router) {}

  onSubmit() {
    this.apiService.postService(this.apiService.apiUrls.forgotPass, this.forgetPass).toPromise().then(res => {
      if (res?.status === true) {
        this.router.navigateByUrl("/login");
      } else {
        this.errorMessage = res?.responseMessage ? res.responseMessage : '';
      }
    });
  }

}
