import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Banco de Proyectos - Poder Judicial';
  isMobileMenuOpen: boolean = false;

  constructor(
    public authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Escuchar eventos de navegación si se requiere cerrar menús
  }

  /**
   * Cierra sesión y redirige a la vista inicial
   */
  logout(): void {
    this.authService.logout();
    this.closeMobileMenu();
    this.router.navigate(['/']);
  }

  /**
   * Alternar menú responsive para móviles
   */
  toggleMobileMenu(): void {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }

  /**
   * Cerrar menú responsive tras hacer clic
   */
  closeMobileMenu(): void {
    this.isMobileMenuOpen = false;
  }

  /**
   * Fallback visual si el logo local no carga
   */
  onLogoError(event: Event): void {
    const imgElement = event.target as HTMLImageElement;
    imgElement.src = 'assets/carrusel/palacio1.jpg';
  }
}