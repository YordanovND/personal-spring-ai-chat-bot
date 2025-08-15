import { Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TitleStrategy, RouterStateSnapshot } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class PageTitleStrategy extends TitleStrategy {
  constructor(private readonly title: Title) { super(); }
  override updateTitle(snapshot: RouterStateSnapshot) {
    const t = this.buildTitle(snapshot);
    this.title.setTitle(t ? `${t} • Nikolay Yordanov` : 'Nikolay Yordanov');
  }
}
