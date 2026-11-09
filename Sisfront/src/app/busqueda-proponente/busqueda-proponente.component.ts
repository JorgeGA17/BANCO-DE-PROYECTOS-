import { Component, OnInit } from '@angular/core';

// Service & Models
import { ProyectoService } from '../service/proyecto.service';
import { Proyecto } from '../models/proyecto';

interface ProponenteProyecto {
  proponente: string;
  xnombreProyecto: string;
  nombreCorte: string;
  nombreEstado: string;
  proyectopk: number;
}

@Component({
  selector: 'app-busqueda-proponente',
  templateUrl: './busqueda-proponente.component.html',
  styleUrls: ['./busqueda-proponente.component.css']
})
export class BusquedaProponenteComponent implements OnInit {
  proyectosOriginales: Proyecto[] = [];
  proponentesProyectos: ProponenteProyecto[] = [];
  proponentesProyectosFiltrados: ProponenteProyecto[] = [];
  searchTextDni: string = '';
  searchTextNombre: string = '';
  searchTextApellido: string = '';

  constructor(private proyectoService: ProyectoService) { }

  ngOnInit(): void {
    this.getAllProyectos();
  }

  getAllProyectos(): void {
    this.proyectoService.getAllProyectos().subscribe(response => {
      this.proyectosOriginales = response;
      this.procesarProyectos();
    });
  }

  procesarProyectos(): void {
    this.proponentesProyectos = [];
    this.proyectosOriginales.forEach(proyecto => {
      if (proyecto.listaNombresPersonal && proyecto.listaNombresPersonal.length > 0) {
        proyecto.listaNombresPersonal.forEach(nombreCompleto => {
          this.proponentesProyectos.push({
            proponente: nombreCompleto,
            xnombreProyecto: proyecto.xnombreProyecto,
            nombreCorte: proyecto.nombreCorte,
            nombreEstado: proyecto.nombreEstado,
            proyectopk: proyecto.proyectoPk
          });
        });
      } else {
        this.proponentesProyectos.push({
          proponente: 'N/A',
          xnombreProyecto: proyecto.xnombreProyecto,
          nombreCorte: proyecto.nombreCorte,
          nombreEstado: proyecto.nombreEstado,
          proyectopk: proyecto.proyectoPk
        });
      }
    });
    this.filtrarProyectos();
  }

  filtrarProyectos(): void {
    if (!this.searchTextDni && !this.searchTextNombre && !this.searchTextApellido) {
      this.proponentesProyectosFiltrados = this.proponentesProyectos;
    } else {
      const nombreBusqueda = this.searchTextNombre.toLowerCase();
      const apellidoBusqueda = this.searchTextApellido.toLowerCase();
      const dniBusqueda = this.searchTextDni.toLowerCase();

      this.proponentesProyectosFiltrados = this.proponentesProyectos.filter(item => {
        const nombreProponente = item.proponente.toLowerCase();
        return (nombreBusqueda && nombreProponente.includes(nombreBusqueda)) ||
          (apellidoBusqueda && nombreProponente.includes(apellidoBusqueda)) ||
          (dniBusqueda && nombreProponente.includes(dniBusqueda));
      });
    }
  }
}