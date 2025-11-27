import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { BASE_URL } from '../constants/constants';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let routerSpy: jasmine.SpyObj<Router>;

  const mockToken = {
    sub: '12345',
    role: 'SELLER',
    exp: Math.floor(Date.now() / 1000) + 3600 // 1 hour expiry
  };

  const encodedMockToken = btoa(JSON.stringify(mockToken)); // simple fake token

  beforeEach(() => {
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AuthService,
        { provide: Router, useValue: routerSpy }
      ]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  // ----------------------------
  //    TOKEN / DECODE TESTS
  // ----------------------------

  it('should decode token on login', () => {
    const credentials = { email: 'test@test.com', password: '123' };

    service.loginNoReload(credentials).subscribe();

    const req = httpMock.expectOne(`${BASE_URL}/api/auth/login`);
    expect(req.request.method).toBe('POST');

    req.flush({ token: encodedMockToken });

    expect(localStorage.getItem('token')).toBe(encodedMockToken);
    expect(service.getUserRole()).toBe('SELLER');
    expect(service.getUserId()).toBe('12345');
  });

  it('should return null userId if no token', () => {
    expect(service.getUserId()).toBeNull();
  });

  // ----------------------------
  //    LOGIN TESTS
  // ----------------------------

  it('should store token and avatar on login', () => {
    const avatarUrl = '/avatar/test.png';

    const credentials = { email: 'a@a.com', password: '123' };

    service.login(credentials).subscribe();

    const req = httpMock.expectOne(`${BASE_URL}/api/auth/login`);
    req.flush({ token: encodedMockToken, avatar: avatarUrl });

    expect(localStorage.getItem('token')).toBe(encodedMockToken);
    expect(service.getAvatar()).toBe(avatarUrl);
  });

  // ----------------------------
  //     SIGNUP TESTS
  // ----------------------------

  it('should call signup endpoint', () => {
    const form = { email: 'x@x.com', password: 'pass' };

    service.signup(form).subscribe();

    const req = httpMock.expectOne(`${BASE_URL}/api/auth/signup`);
    expect(req.request.method).toBe('POST');
    req.flush({ message: 'ok' });
  });

  // ----------------------------
  //     LOGGED IN CHECK
  // ----------------------------

  it('should return true when token is valid and not expired', () => {
    localStorage.setItem('token', encodedMockToken);
    (service as any).safeDecode(encodedMockToken);

    expect(service.isLoggedIn()).toBeTrue();
  });

  it('should return false when token is expired', () => {
    const expiredToken = btoa(
      JSON.stringify({
        sub: '1',
        role: 'CLIENT',
        exp: Math.floor(Date.now() / 1000) - 10
      })
    );

    localStorage.setItem('token', expiredToken);
    (service as any).safeDecode(expiredToken);

    expect(service.isLoggedIn()).toBeFalse();
  });

  // ----------------------------
  //     LOGOUT TESTS
  // ----------------------------

  it('should clear token on logout', () => {
    localStorage.setItem('token', 'XYZ');

    const routerSpy = jasmine.createSpyObj('Router', ['navigate'], { url: '/' });

    service.logout();

    expect(localStorage.getItem('token')).toBeNull();
  });

  // ----------------------------
  //     AVATAR TESTS
  // ----------------------------

  it('should set and get avatar', () => {
    service.setAvatar('/test/avatar.png');
    expect(service.getAvatar()).toBe('/test/avatar.png');
  });
});
