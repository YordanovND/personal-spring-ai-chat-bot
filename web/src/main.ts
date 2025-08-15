import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
// No provideForms import here

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withFetch()),
  ],
}).catch(err => console.error(err));
