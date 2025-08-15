import { Component, ElementRef, ViewChild, signal, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from './chat.service';
import { AutosizeDirective } from '../../shared/autosize.directive';
import { MarkdownPipe } from '../../shared/markdown.pipe';

type Role = 'user' | 'assistant';
interface ChatMessage { id: string; role: Role; content: string; createdAt: number; }
const uid = () => Math.random().toString(36).slice(2);

@Component({
  standalone: true,
  selector: 'app-chatbot',
  templateUrl: 'chatbot.component.html',
  styleUrls: ['chatbot.component.scss'],
  imports: [CommonModule, FormsModule, AutosizeDirective, MarkdownPipe],
})
export class ChatbotComponent {
  @ViewChild('scroll') scrollRef!: ElementRef<HTMLDivElement>;
  draft = '';
  private key = 'chat-history-v1';
  messages = signal<ChatMessage[]>(this.load());
  thinking = signal(false);

  constructor(private api: ChatService) {
    effect(() => localStorage.setItem(this.key, JSON.stringify(this.messages())));
  }

  track = (_: number, m: ChatMessage) => m.id;
  fill(t: string) { this.draft = t; }
  onEnter(e: Event) {
    const ke = e as KeyboardEvent;
    if (!ke.shiftKey) {
      ke.preventDefault();
      this.send();
    }
  }
  clear() { this.messages.set([]); }

  send() {
    const content = this.draft.trim();
    if (!content || this.thinking()) return;

    const user: ChatMessage = { id: uid(), role: 'user', content, createdAt: Date.now() };
    this.messages.update(a => [...a, user]);
    this.draft = '';
    this.scroll();

    this.thinking.set(true);
    this.api.chat({ message: content, scope: 'auto' }).subscribe({
      next: r => {
        const bot: ChatMessage = { id: uid(), role: 'assistant', content: r.reply, createdAt: Date.now() };
        this.messages.update(a => [...a, bot]);
        this.scroll();
      },
      error: () => {
        this.messages.update(a => [...a, {
          id: uid(), role: 'assistant',
          content: 'Sorry—something went wrong. Please try again.', createdAt: Date.now()
        }]);
      },
      complete: () => this.thinking.set(false)
    });
  }

  private scroll() {
    setTimeout(() => {
      const el = this.scrollRef?.nativeElement;
      if (el) el.scrollTop = el.scrollHeight;
    }, 0);
  }

  private load(): ChatMessage[] {
    try { return JSON.parse(localStorage.getItem(this.key) || '[]'); } catch { return []; }
  }
}
