import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { RagComponent } from './rag/rag.component';
import { ListaCortesComponent } from "./Corte/lista-cortes/lista-cortes.component";
import { ListaProyectosComponent } from "./Proyecto/lista-proyectos/lista-proyectos.component";
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Módulos de Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { MatSelectModule } from '@angular/material/select';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRippleModule } from '@angular/material/core';
import { MatToolbarModule } from '@angular/material/toolbar'; // <-- AGREGADO

import { HomeComponent } from './home/home.component';
import { SliderComponent } from './home/slider/slider.component';
import { FooterComponent } from './home/footer/footer.component';
import { EstadisticaComponent } from './Estadistica/estadistica/estadistica.component';
import { DetalleComponent } from './Corte/lista-cortes/detalle/detalle.component';
import { BusquedaAvanzadaComponent } from './busqueda-avanzada/busqueda-avanzada.component';
import { BusquedaProponenteComponent } from './busqueda-proponente/busqueda-proponente.component';
import { DetalleProyectoComponent } from "./Proyecto/detalle-proyecto/detalle-proyecto.component";
import { TokenInterceptor } from "./interceptor/token.interceptor";
import { LoginComponent } from "./login/login.component";
import { CreacionComponent } from "./Proyecto/creacion/creacion.component";

@NgModule({
  declarations: [
    AppComponent,
    RagComponent,
    ListaProyectosComponent,
    ListaCortesComponent,
    HomeComponent,
    SliderComponent,
    FooterComponent,
    EstadisticaComponent,
    DetalleProyectoComponent,
    DetalleComponent,
    BusquedaAvanzadaComponent,
    BusquedaProponenteComponent,
    CreacionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    NgxPaginationModule,

    // Angular Material Modules
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatChipsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDividerModule,
    MatSelectModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatRippleModule,
    MatToolbarModule // <-- AGREGADO
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
