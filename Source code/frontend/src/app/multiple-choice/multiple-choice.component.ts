import { Component, OnInit } from '@angular/core';
import { MultipleChoiceQuest } from '../models/MultipleChoiceQuest';
import { ApiService } from '../services/api.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MultipleChoiceAns } from '../models/MultipleChoiceAns';
declare var $: any;

@Component({
  selector: 'app-multiple-choice',
  templateUrl: './multiple-choice.component.html',
  styleUrls: ['./multiple-choice.component.css']
})
export class MultipleChoiceComponent implements OnInit {
  userName: string | null = localStorage.getItem('token');
  question: MultipleChoiceQuest = {};
  getQuestionApi?: string;
  submitAnswerApi?: string;
  languageSelected:string = '';
  answer?: MultipleChoiceAns;
  displayResults: boolean = false;
  resultModal:string = 'resultModal';
  constructor(
    private apiService: ApiService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    ) {}

  ngOnInit(): void {
    this.activatedRoute.queryParamMap.subscribe(params => {
      if (params.has('languageSelected')) {
        this.languageSelected = params.get('languageSelected')!;
        this.setApiUrls();
        this.getQuestion();
      } else { 
        this.router.navigateByUrl('/home');
      }
    });
  }

  setApiUrls() {
    if (this.router.url.startsWith('/activity1')) {
      this.getQuestionApi = this.apiService.apiUrls.activity1Questions;
      this.submitAnswerApi = this.apiService.apiUrls.activity1Answer;
    } else {
      this.getQuestionApi = this.apiService.apiUrls.activity2Questions;
      this.submitAnswerApi = this.apiService.apiUrls.activity2Answer;
    }
  }
  
  onSubmit() {
    this.apiService.postService(this.submitAnswerApi!, this.question).toPromise().then(res => {
      if (res) {
        this.answer = res.data as MultipleChoiceAns;
        $(`#${this.resultModal}`).modal('show');
        if (!this.answer.quizReport) {
          this.getQuestion();
        } else {
          this.displayResults = true;
        }
      }
    });
  }

  getQuestion() {
    this.apiService.postService(this.getQuestionApi!, {username: this.userName, targetLanguage: this.languageSelected}).toPromise().then(res => {
      if (res) {
        this.question = res.data as MultipleChoiceQuest;
      }
    });
  }

}
