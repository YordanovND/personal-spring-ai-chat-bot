import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';

type Msg = { role: 'user' | 'assistant'; content: string };

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  template: `
    <div class="toolbar">
      <label>
        Scope:
        <select [(ngModel)]="scope">
          <option value="auto">auto</option>
          <option value="job">job</option>
          <option value="interests">interests</option>
          <option value="profile">profile</option>
          <option value="all">all</option>
        </select>
      </label>
      <input [(ngModel)]="user" placeholder="user id" />
      <button (click)="clear()">Clear</button>
    </div>

    <div class="chat">
      <div *ngFor="let m of messages" [class.me]="m.role==='user'" class="bubble">
        {{ m.content }}
      </div>
    </div>

    <form (ngSubmit)="send()" class="composer">
      <input [(ngModel)]="message" name="m" placeholder="Type a message..." />
      <button type="submit">Send</button>
    </form>
  `,
  styles: [`
    .toolbar { display:flex; gap:.75rem; align-items:center; padding:.5rem; }
    .chat { padding:1rem; display:flex; flex-direction:column; gap:.5rem; min-height:60vh; }
    .bubble { background:#f4f6f8; padding:.5rem .75rem; border-radius:.75rem; max-width:60ch; }
    .bubble.me { background:#dff3ff; align-self:flex-end; }
    .composer { display:flex; gap:.5rem; padding: .75rem; border-top:1px solid #eee; }
    input, select { padding:.4rem .6rem; }
    button { padding:.45rem .8rem; }
  `]
})
export class AppComponent {
  scope: string = 'auto';
  user = 'me';
  message = '';
  messages: Msg[] = [{ role: 'assistant', content: 'Connected. Scope: auto. Type your message and press Enter.' }];

  constructor(private http: HttpClient) {}

  async send() {
    const text = this.message.trim();
    if (!text) return;
    this.messages.push({ role: 'user', content: text });
    this.message = '';

    try {
      const res = await this.http.post<{ reply: string }>(
        '/api/ai/chat',
        { message: text, user: this.user, scope: this.scope }
      ).toPromise();

      this.messages.push({ role: 'assistant', content: res?.reply || '(no content)' });
    } catch (e: any) {
      this.messages.push({ role: 'assistant', content: 'Error: ' + (e?.message ?? e) });
    }
  }

  clear() {
    this.messages = [];
  }
}
