import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { TranslateReq } from '../models/TranslateReq';

@Component({
  selector: 'app-translation',
  templateUrl: './translation.component.html',
  styleUrls: ['./translation.component.css']
})
export class TranslationComponent implements OnInit {
  languageCodes: string[] = [];
  translateReq: TranslateReq = { sourceLanguage: '', inputText: '', targetLanguage: ''};
  answer: string = '';
  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.getLanguageCodes();
  }

  getLanguageCodes() {
    this.apiService.getService(this.apiService.apiUrls.getLanguageCodes).toPromise().then(res => {
      if (res && res.data) {
        this.languageCodes = res.data as string[];
        this.translateReq.sourceLanguage = this.languageCodes[0];
        this.translateReq.targetLanguage = this.languageCodes[0];
      }
    });
  }

  translate() {
    this.apiService.postService(this.apiService.apiUrls.translate, this.translateReq).toPromise().then(res => {
      if (res && res.data) {
        this.answer = res.data;
      }
    });
  }

}
