import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../service/auth.service';

export const loginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    authService.logout();
    // 🔹 Aquí decides a dónde mandarlo después de cerrar sesión:
    router.navigate(['/home']); // lo mando al home
    return false; // Bloquea la navegación al login
  }

  return true; // deja entrar si no tiene sesión
};
