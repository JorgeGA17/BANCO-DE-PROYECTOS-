import { Component, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

// Services & Dependencies
import { AuthService } from '../service/auth.service';
import Swal from 'sweetalert2';

// Angular Material Modules
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule, 
    CommonModule, 
    HttpClientModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  hidePassword: boolean = true;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      xcorreoInstitucional: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      console.log('Valores del formulario:', this.loginForm.value);

      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          console.log('Login exitoso', response);
          Swal.fire({
            icon: 'success',
            title: '¡Acceso exitoso!',
            text: 'Serás redirigido en breve.',
            confirmButtonColor: '#C00000'
          });
          this.router.navigate(['/home']);
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error en el login:', error);

          let errorMessage = 'Ha ocurrido un error inesperado.';

          if (error.status === 401 || error.status === 403) {
            errorMessage = 'Correo o contraseña incorrectos.';
          } else if (error.status === 400) {
            errorMessage = 'Datos de solicitud incorrectos.';
          }

          Swal.fire({
            icon: 'error',
            title: 'Error de autenticación',
            text: errorMessage,
            confirmButtonColor: '#C00000'
          });
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  /**
   * Notificación Informativa Institucional de Restablecimiento.
   * Evita vulnerabilidades de seguridad en la interfaz al no exponer tokens
   * ni permitir cambios no autorizados sin verificación de correo real.
   */
  recuperarContrasena(): void {
    Swal.fire({
      icon: 'info',
      title: 'Restablecimiento de Contraseña',
      html: `
        <p style="font-size: 0.95rem; color: #374151; margin-bottom: 12px; text-align: justify;">
          Por políticas de ciberseguridad e integridad de accesos del Poder Judicial, el restablecimiento de contraseñas es gestionado de forma auditada.
        </p>
        <p style="font-size: 0.9rem; color: #4B5563; text-align: justify;">
          Si ha olvidado sus credenciales, solicite el restablecimiento de clave a la <strong>Unidad de Gestión de Despacho Judicial (UGDJ)</strong> o contacte al administrador del sistema en <a href="mailto:ugdj_cepj@pj.gob.pe" style="color: #C00000; font-weight: bold;">ugdj_cepj@pj.gob.pe</a>.
        </p>
      `,
      confirmButtonText: 'Entendido',
      confirmButtonColor: '#C00000'
    });
  }
}