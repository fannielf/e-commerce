import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { AuthService } from './services/auth.service';
import { Router, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BASE_URL } from './constants/constants';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, RouterLink, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  profileRoute = '/auth';
  profileImageUrl: string | null = null;
  isLoggedIn = false;
  showProfile = false;

  constructor(private auth: AuthService, private router: Router) {
    this.updateProfileState();
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateProfileState();
      }
    });
  }

  private updateProfileState() {
    this.isLoggedIn = this.auth.isLoggedIn();
    const role = this.auth.getUserRole();
    this.profileRoute =
      role === 'CLIENT' ? '/client-profile' :
      role === 'SELLER' ? '/seller-profile' :
      '/auth';

    const url = this.router.url ?? '';
    this.showProfile = this.isLoggedIn && !url.includes('auth');

      const avatarPath = this.auth.getAvatar(); // e.g., "/api/media/avatar/7d049140-0b48-4cb0-9891-a111697ca084.png"
      this.profileImageUrl = avatarPath
        ? `${BASE_URL}${avatarPath}`  // prepend domain
        : 'assets/default.jpg';
  }

  logout() {
    this.auth.logout();
    this.showProfile = false;
  }
}
