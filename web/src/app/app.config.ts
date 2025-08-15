// web/src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { TitleStrategy } from '@angular/router';
import { PageTitleStrategy } from './shared/title.strategy';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    { provide: TitleStrategy, useClass: PageTitleStrategy },
  ],
};
