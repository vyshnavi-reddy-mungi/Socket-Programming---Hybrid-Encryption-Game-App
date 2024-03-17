import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForgetPasswordComponent } from './forget-password/forget-password.component';
import { GameChoiceComponent } from './game-choice/game-choice.component';
import { HomeComponent } from './home/home.component';
import { LoginGuardService } from './login-guard/login-guard.service';
import { LoginComponent } from './login/login.component';
import { MultipleChoiceComponent } from './multiple-choice/multiple-choice.component';
import { SignupComponent } from './signup/signup.component';
import { TranslationComponent } from './translation/translation.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [LoginGuardService],
    children: [
      {
        path: '',
        component: GameChoiceComponent,
        canActivate: [LoginGuardService]
      },
      {
        path: 'activity1',
        component: MultipleChoiceComponent,
        canActivate: [LoginGuardService]
      },
      {
        path: 'activity2',
        component: MultipleChoiceComponent,
        canActivate: [LoginGuardService]
      },
      {
        path: 'translation',
        component: TranslationComponent,
        canActivate: [LoginGuardService]
      }
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'sign-up',
    component: SignupComponent
  },
  {
    path: 'forget-password',
    component: ForgetPasswordComponent
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
