import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProyectoService } from '../../service/proyecto.service';
import { CorteService } from '../../service/corte.service';
import { EstadoService } from '../../service/estado.service';
import { EspecialidadService } from '../../service/especialidad.service';
import { JerarquiaService } from '../../service/jerarquia.service';
import { EjeService } from '../../service/eje.service';
import { EtiquetaService } from '../../service/etiqueta.service';
import { PersonalService } from '../../service/personal.service';
import { forkJoin } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-creacion',
  templateUrl: './creacion.component.html',
  styleUrls: ['./creacion.component.css']
})
export class CreacionComponent implements OnInit {

  creacion: FormGroup;
  cortes: any[] = [];
  estados: any[] = [];
  especialidades: any[] = [];
  jerarquias: any[] = [];
  ejes: any[] = [];
  etiquetas: any[] = [];
  personal: any[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private proyectoService: ProyectoService,
    private cortesService: CorteService,
    private estadosService: EstadoService,
    private especialidadesService: EspecialidadService,
    private jerarquiasService: JerarquiaService,
    private ejesService: EjeService,
    private etiquetasService: EtiquetaService,
    private personalService: PersonalService
  ) {
    this.creacion = this.fb.group({
      xnombreProyecto: ['', Validators.required],
      xproblematica: ['', Validators.required],
      xresumen: ['', Validators.required],
      xobjetivoGeneral: ['', Validators.required],
      xinnovacion: ['', Validators.required],
      ximpacto: ['', Validators.required],
      xsostenibilidad: ['', Validators.required],
      xreplicabilidad: ['', Validators.required],
      xconceptoEval: ['', Validators.required],
      xdescripEval: ['', Validators.required],
      corteId: [null, Validators.required],
      estadoId: [null, Validators.required],
      especialidadIds: [[]],
      jerarquiaIds: [[]],
      ejeIds: [[]],
      etiquetaIds: [[]],
      personalIds: [[]]
    });
  }

  ngOnInit(): void {
    forkJoin({
      cortes: this.cortesService.getAllCortes(),
      estados: this.estadosService.getAllEstados(),
      especialidades: this.especialidadesService.getAllEspecialidades(),
      jerarquias: this.jerarquiasService.getAllJerarquias(),
      ejes: this.ejesService.getAllEjes(),
      etiquetas: this.etiquetasService.getAllEtiquetas(),
      personal: this.personalService.getAllPersonas()
    }).subscribe(
      (data) => {
        this.cortes = data.cortes;
        this.estados = data.estados;
        this.especialidades = data.especialidades;
        this.jerarquias = data.jerarquias;
        this.ejes = data.ejes;
        this.etiquetas = data.etiquetas;
        this.personal = data.personal;
      },
      (error) => {
        console.error('Error al cargar datos:', error);
      }
    );
  }

  // --- MÉTODOS DE SELECCIÓN REPLICADOS DE TU CÓDIGO ---

  onEspecialidadSelect(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const selectedId = parseInt(target.value, 10);
    const control = this.creacion.get('especialidadIds');

    if (selectedId && control && !control.value.includes(selectedId)) {
      const updatedArray = [...control.value, selectedId];
      control.setValue(updatedArray);
    }
  }

  onJerarquiaSelect(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const selectedId = parseInt(target.value, 10);
    const control = this.creacion.get('jerarquiaIds');

    if (selectedId && control && !control.value.includes(selectedId)) {
      const updatedArray = [...control.value, selectedId];
      control.setValue(updatedArray);
    }
  }

  onEjeSelect(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const selectedId = parseInt(target.value, 10);
    const control = this.creacion.get('ejeIds');

    if (selectedId && control && !control.value.includes(selectedId)) {
      const updatedArray = [...control.value, selectedId];
      control.setValue(updatedArray);
    }
  }

  onEtiquetaSelect(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const selectedId = parseInt(target.value, 10);
    const control = this.creacion.get('etiquetaIds');

    if (selectedId && control && !control.value.includes(selectedId)) {
      const updatedArray = [...control.value, selectedId];
      control.setValue(updatedArray);
    }
  }

  onPersonalSelect(event: Event): void {
    const target = event.target as HTMLSelectElement;
    const selectedId = parseInt(target.value, 10);
    const control = this.creacion.get('personalIds');

    if (selectedId && control && !control.value.includes(selectedId)) {
      const updatedArray = [...control.value, selectedId];
      control.setValue(updatedArray);
    }
  }

  removeSeleccionado(arrayName: string, id: number): void {
    const control = this.creacion.get(arrayName);
    if (control) {
      const updatedArray = control.value.filter((itemId: number) => itemId !== id);
      control.setValue(updatedArray);
    }
  }

  // --- MÉTODOS DE BÚSQUEDA DE NOMBRE ---
  // Estos métodos se mantienen porque son la forma correcta de buscar un nombre por ID.

  getNombreEspecialidad(id: number): string {
    const item = this.especialidades.find(i => i.especialidadPk === id);
    return item ? item.xnombre : '';
  }

  getNombreJerarquia(id: number): string {
    const item = this.jerarquias.find(i => i.jerarquiaPk === id);
    return item ? item.xnombre : '';
  }

  getNombreEje(id: number): string {
    const item = this.ejes.find(i => i.ejePk === id);
    return item ? item.xnombre : '';
  }

  getNombreEtiqueta(id: number): string {
    const item = this.etiquetas.find(i => i.etiquetaPk === id);
    return item ? item.xnombre : '';
  }

  getNombrePersonal(id: number): string {
    const item = this.personal.find(i => i.personalPk === id);
    return item ? `${item.xnombre} ${item.xapellido}` : '';
  }

onSubmit(): void {
  if (this.creacion.valid) {
    const proyectoData = this.creacion.value;

    this.proyectoService.createProyecto(proyectoData).subscribe(
      (response) => {
        // Muestra el Swal de éxito
        Swal.fire({
          title: '¡Éxito!',
          text: 'El proyecto ha sido creado correctamente.',
          icon: 'success',
          confirmButtonText: 'Aceptar'
        }).then(() => {
          // Redirige después de que el usuario haga clic en "Aceptar"
          this.router.navigate(['/lista-proyectos']);
        });
      },
      (error) => {
        // Muestra un Swal de error si algo sale mal
        console.error('Error al crear el proyecto', error);
        Swal.fire({
          title: 'Error',
          text: 'Hubo un problema al crear el proyecto.',
          icon: 'error',
          confirmButtonText: 'Cerrar'
        });
      }
    );
  } else {
    Swal.fire({
      title: 'Atención',
      text: 'El formulario no es válido. Por favor, revise los campos.',
      icon: 'warning'
    });
  }
}
}