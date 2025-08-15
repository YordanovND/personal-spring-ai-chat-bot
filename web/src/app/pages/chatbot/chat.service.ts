import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

type Role = 'user' | 'assistant';
export interface ChatRequest {
  message: string;
  user?: string;     // backend supports these
  session?: string;
  scope?: 'job'|'interests'|'profile'|'all'|'auto';
}
export interface ChatResponse { reply: string; }

@Injectable({ providedIn: 'root' })
export class ChatService {
  constructor(private http: HttpClient) {}
  chat(req: ChatRequest) {
    return this.http.post<ChatResponse>('/api/ai/chat', req);
  }
}
