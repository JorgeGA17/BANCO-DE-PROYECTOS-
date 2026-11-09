import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth.service'; // Asegúrate de que la ruta sea correcta

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    // El usuario está logueado, permite el acceso a la ruta.
    return true;
  } else {
    // El usuario no está logueado, redirige a la página de login.
    router.navigate(['/login']);
    return false;
  }
};