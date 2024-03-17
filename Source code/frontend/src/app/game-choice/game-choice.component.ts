import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-game-choice',
  templateUrl: './game-choice.component.html',
  styleUrls: ['./game-choice.component.css']
})
export class GameChoiceComponent implements OnInit {

  linkUrl?: string;
  languageSelected: string = 'te';

  constructor() {}

  ngOnInit(): void {
    
  }

  setRouteUrl(linkUrl: string) {
    this.linkUrl = linkUrl;
  }
  
}
