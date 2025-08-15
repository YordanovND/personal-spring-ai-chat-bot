import { AfterViewInit, Directive, ElementRef, HostListener } from '@angular/core';

@Directive({ selector: 'textarea[autosize]', standalone: true })
export class AutosizeDirective implements AfterViewInit {
  constructor(private el: ElementRef<HTMLTextAreaElement>) {}
  ngAfterViewInit() { setTimeout(() => this.resize(), 0); }
  @HostListener('input') onInput() { this.resize(); }
  private resize() {
    const ta = this.el.nativeElement;
    ta.style.height = 'auto';
    ta.style.height = Math.min(220, ta.scrollHeight) + 'px';
  }
}
