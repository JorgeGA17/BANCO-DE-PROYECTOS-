import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListaProyectosComponent } from './Proyecto/lista-proyectos/lista-proyectos.component';
import { ListaCortesComponent } from './Corte/lista-cortes/lista-cortes.component';
import { HomeComponent } from './home/home.component';
import { EstadisticaComponent } from './Estadistica/estadistica/estadistica.component';
import { DetalleComponent } from './Corte/lista-cortes/detalle/detalle.component';
import { BusquedaAvanzadaComponent } from './busqueda-avanzada/busqueda-avanzada.component';
import { BusquedaProponenteComponent } from './busqueda-proponente/busqueda-proponente.component';
import { DetalleProyectoComponent } from './Proyecto/detalle-proyecto/detalle-proyecto.component';
import { LoginComponent } from './login/login.component';

// Importa el guardián de autenticación.
import { authGuard } from './guards/auth.guard';
import { CreacionComponent } from './Proyecto/creacion/creacion.component';
import { EditarComponent } from './Proyecto/editar/editar.component';
import { EditarProyectoComponent } from './Proyecto/editar/editar-proyecto/editar-proyecto.component';
import { loginGuard } from './guards/login.guard';

const routes: Routes = [
  // Página inicial pública
  { path: '', component: HomeComponent },

  // Login público
  { path: 'login', component: LoginComponent, canActivate: [loginGuard] }, // para cerrar sesión cuando ingreso manualmente a login

  // ===== RUTAS PÚBLICAS =====
  { path: 'home', component: HomeComponent },
  { path: 'lista-proyectos', component: ListaProyectosComponent },
  { path: 'lista-cortes', component: ListaCortesComponent },
  { path: 'estadistica', component: EstadisticaComponent },
  { path: 'detalle-proyecto', component: DetalleProyectoComponent },
  { path: 'detalle-corteproyecto', component: DetalleComponent },
  { path: 'busqueda-avanzada', component: BusquedaAvanzadaComponent },
  { path: 'busqueda-proponente', component: BusquedaProponenteComponent },
  { path: 'detalle-proyecto/:proyectopk', component: DetalleProyectoComponent },

  // ===== RUTAS PRIVADAS (CRUD) =====
  { path: 'creacion', component: CreacionComponent, canActivate: [authGuard] },
  { path: 'editar', component: EditarComponent, canActivate: [authGuard] },
  { path: 'editar-proyecto/:proyectopk', component: EditarProyectoComponent, canActivate: [authGuard] },



  // Ruta fallback
  { path: '**', redirectTo: '' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }