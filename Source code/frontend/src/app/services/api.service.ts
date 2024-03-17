import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, throwError } from 'rxjs';
import { GeneralResponse } from '../models/GenericResponse';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

    baseUrl: string = 'http://localhost:8080';

    apiUrls = {
        register: '/api/user/register',
        login: '/api/user/login',
        forgotPass: '/api/user/forgot/password',
        forgotUserName: '/api/user/forgot/username',
        activity1Questions: '/activity1/mcq/start',
        activity2Questions: '/activity2/new/mcq/start',
        activity1Answer: '/activity1/mcq/answer',
        activity2Answer: '/activity2/new/mcq/answer',
        getLanguageCodes: '/translate/languagecodes',
        translate: '/translate/source/target',
    }

  constructor(private http: HttpClient) { }

  getService(url: string) {
    return this.http.get(this.baseUrl + url)
    .pipe(map((response:GeneralResponse) => {
        if (response.status === true) {
            return response;
        } else {
            this.handleError(response);
            return response;
        }
    }))
    .pipe(catchError(this.handleError))
  }

  postService(url: string, data: any) {   

    return this.http.post(this.baseUrl + url, data).pipe(map((response:GeneralResponse) => {
        if (response.status === true) {
            return response;
        } else {
            this.handleError(response);
            return response;
        }
    }))
    .pipe(catchError(this.handleError))
  }

  handleError = (error: any) => {
    return throwError(error);
  }

}