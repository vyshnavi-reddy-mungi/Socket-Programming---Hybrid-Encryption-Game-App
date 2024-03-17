import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginDto } from '../models/LoginDto';
import { ApiService } from '../services/api.service';
// import { EncryptionService } from './encryption.service';

// import { encryptedData } from './encryption';
// import publicKey from './public-cert-file.cer';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginDto:LoginDto = {};
  errorMessage: string = '';

  // constructor(private router: Router, private apiService: ApiService, private encryptService: EncryptionService ) {
   constructor(private router: Router, private apiService: ApiService) {

  }

  onSubmit() {
  // const loginString = JSON.stringify(this.loginDto);
  // console.log(loginString);
  //  const encryptedData = this.encryptService.encryptData(loginString);
  // encryptedData(loginString);
  // this.apiService.postService(this.apiService.apiUrls.login, encryptedData).toPromise().then(res => {
   
    this.apiService.postService(this.apiService.apiUrls.login, this.loginDto).toPromise().then(res => {
     if (res) {
        if (res.status === true) {
          localStorage.setItem('token', this.loginDto.username!);
          this.router.navigateByUrl("home");
        } else {
          this.errorMessage = res.responseMessage ? res.responseMessage : '';
        }
      }
    });
  }

  
}
