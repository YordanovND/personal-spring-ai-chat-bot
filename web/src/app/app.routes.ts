// web/src/app/app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent),
    title: 'Home'
    },
  {
    path: 'ask',
    loadComponent: () => import('./pages/chatbot/chatbot.component').then(m => m.ChatbotComponent),
    title: 'Ask Me'
    },
  {
    path: '**',
    redirectTo: ''
    }
];
