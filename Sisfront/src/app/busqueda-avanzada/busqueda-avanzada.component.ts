import { Component, OnInit } from '@angular/core';
import { Proyecto } from '../models/proyecto';
import { ProyectoService } from '../service/proyecto.service';
import { CorteService } from '../service/corte.service';
import { Corte } from '../models/corte';
import { Eje } from '../models/Eje';
import { EjeService } from '../service/eje.service';
import { Especialidad } from '../models/especialidad';
import { EspecialidadService } from '../service/especialidad.service';
import { Jerarquia } from '../models/Jerarquia';
import { JerarquiaService } from '../service/jerarquia.service';
import { Estado } from '../models/estado';
import { EstadoService } from '../service/estado.service';
import { Etiqueta } from '../models/Etiqueta';
import { EtiquetaService } from '../service/etiqueta.service';

@Component({
  selector: 'app-busqueda-avanzada',
  templateUrl: './busqueda-avanzada.component.html',
  styleUrls: ['./busqueda-avanzada.component.css']
})
export class BusquedaAvanzadaComponent implements OnInit {

  proyectos: Proyecto[];
  proyectosFiltrados: Proyecto[];
  cortes: Corte[];
  especialidades: Especialidad[];
  ejes: Eje[];
  jerarquias: Jerarquia[];
  estados: Estado[];
  etiquetas: Etiqueta[];
  
  corteSeleccionada: number | null | undefined;
  ejeSeleccionado: number | null | undefined;
  especialidadSeleccionada: number | null | undefined;
  jerarquiaSeleccionada: number | null | undefined;
  estadoSeleccionado: number | null | undefined;
  etiquetaSeleccionada: number | null | undefined;

  constructor(
    private proyectoService: ProyectoService,
    private corteService: CorteService,
    private ejeService: EjeService,
    private especialidadService: EspecialidadService,
    private jerarquiaService: JerarquiaService,
    private estadoService: EstadoService,
    private etiquetaService: EtiquetaService
  ) { }

  ngOnInit(): void {
    this.getProyectos();
    this.getCortes();
    this.getEjes();
    this.getEspecialidades();
    this.getJerarquias();
    this.getEstados();
    this.getEtiquetas();
  }

  private getProyectos() {
    this.proyectoService.getAllProyectos().subscribe(dato => {
      this.proyectos = dato;
      this.proyectosFiltrados = this.proyectos;
    });
  }

  private getCortes() {
    this.corteService.getAllCortes().subscribe(dato => {
      this.cortes = dato;
    });
  }

  private getEjes() {
    this.ejeService.getAllEjes().subscribe(dato => {
      this.ejes = dato;
    });
  }

  private getEspecialidades() {
    this.especialidadService.getAllEspecialidades().subscribe(dato => {
      this.especialidades = dato;
    });
  }

  private getJerarquias() {
    this.jerarquiaService.getAllJerarquias().subscribe(dato => {
      this.jerarquias = dato;
    });
  }

  private getEstados() {
    this.estadoService.getAllEstados().subscribe(dato => {
      this.estados = dato;
    });
  }

  private getEtiquetas() {
    this.etiquetaService.getAllEtiquetas().subscribe(dato => {
      this.etiquetas = dato;
    });
  }

  filtrarProyectos() {
    this.proyectosFiltrados = this.proyectos.filter(proyecto => {
      const corteId = this.corteSeleccionada ? Number(this.corteSeleccionada) : undefined;
      const ejeId = this.ejeSeleccionado ? Number(this.ejeSeleccionado) : undefined;
      const especialidadId = this.especialidadSeleccionada ? Number(this.especialidadSeleccionada) : undefined;
      const jerarquiaId = this.jerarquiaSeleccionada ? Number(this.jerarquiaSeleccionada) : undefined;
      const estadoId = this.estadoSeleccionado ? Number(this.estadoSeleccionado) : undefined;
      const etiquetaId = this.etiquetaSeleccionada ? Number(this.etiquetaSeleccionada) : undefined;

      const filtroCorte = !corteId || proyecto.corteId == corteId;
      const filtroEstado = !estadoId || proyecto.estadoId == estadoId;

      const filtroJerarquia = !jerarquiaId || (proyecto.jerarquiaIds && proyecto.jerarquiaIds.includes(jerarquiaId));
      const filtroEspecialidad = !especialidadId || (proyecto.especialidadIds && proyecto.especialidadIds.includes(especialidadId));
      const filtroEje = !ejeId || (proyecto.ejeIds && proyecto.ejeIds.includes(ejeId));
      const filtroEtiqueta = !etiquetaId || (proyecto.etiquetaIds && proyecto.etiquetaIds.includes(etiquetaId));

      return filtroCorte && filtroEstado && filtroJerarquia && filtroEspecialidad && filtroEje && filtroEtiqueta;
    });
  }

  reiniciarBusqueda() {
    this.etiquetaSeleccionada = undefined;
    this.estadoSeleccionado = undefined;
    this.jerarquiaSeleccionada = undefined;
    this.especialidadSeleccionada = undefined;
    this.ejeSeleccionado = undefined;
    this.corteSeleccionada = undefined;
    this.proyectosFiltrados = this.proyectos;
  }
}