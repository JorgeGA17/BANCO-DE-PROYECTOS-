import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

// Services
import { ProyectoService } from '../../../service/proyecto.service';
import { EspecialidadService } from '../../../service/especialidad.service';
import { JerarquiaService } from '../../../service/jerarquia.service';
import { EjeService } from '../../../service/eje.service';
import { EtiquetaService } from '../../../service/etiqueta.service';
import { PersonalService } from '../../../service/personal.service';
import { CorteService } from '../../../service/corte.service';
import { EstadoService } from '../../../service/estado.service';

// Angular Material Modules
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-editar-proyecto',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterLink,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatDividerModule
  ],
  templateUrl: './editar-proyecto.component.html',
  styleUrls: ['./editar-proyecto.component.css']
})
export class EditarProyectoComponent implements OnInit {
  creacion!: FormGroup;
  id!: number;

  // Catálogos
  especialidades: any[] = [];
  jerarquias: any[] = [];
  ejes: any[] = [];
  etiquetas: any[] = [];
  personal: any[] = [];
  cortes: any[] = [];
  estados: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private proyectoService: ProyectoService,
    private especialidadService: EspecialidadService,
    private jerarquiaService: JerarquiaService,
    private ejeService: EjeService,
    private etiquetaService: EtiquetaService,
    private personalService: PersonalService,
    private corteService: CorteService,
    private estadoService: EstadoService
  ) {}

  ngOnInit(): void {
    this.id = +this.route.snapshot.paramMap.get('proyectopk')!;

    this.creacion = this.fb.group({
      proyectoPk: [this.id],
      xnombreProyecto: ['', Validators.required],
      xproblematica: [''],
      xresumen: [''],
      xobjetivoGeneral: [''],
      xinnovacion: [null],
      ximpacto: [null],
      xsostenibilidad: [null],
      xreplicabilidad: [null],
      xconceptoEval: [''],
      xdescripEval: [''],
      corteId: [null, Validators.required],
      estadoId: [null, Validators.required],
      especialidadIds: [[]],
      jerarquiaIds: [[]],
      ejeIds: [[]],
      etiquetaIds: [[]],
      personalIds: [[]]
    });

    // Cargar catálogos
    this.especialidadService.getAllEspecialidades().subscribe(d => this.especialidades = d);
    this.jerarquiaService.getAllJerarquias().subscribe(d => this.jerarquias = d);
    this.ejeService.getAllEjes().subscribe(d => this.ejes = d);
    this.etiquetaService.getAllEtiquetas().subscribe(d => this.etiquetas = d);
    this.personalService.getAllPersonas().subscribe(d => this.personal = d);
    this.corteService.getAllCortes().subscribe(d => this.cortes = d);
    this.estadoService.getAllEstados().subscribe(d => this.estados = d);

    // Cargar datos del proyecto
    this.proyectoService.getProyectoById(this.id).subscribe(p => {
      this.creacion.patchValue({
        ...p,
        corteId: p.corteId,
        estadoId: p.estadoId,
        especialidadIds: p.especialidadIds || [],
        jerarquiaIds: p.jerarquiaIds || [],
        ejeIds: p.ejeIds || [],
        etiquetaIds: p.etiquetaIds || [],
        personalIds: p.personalIds || []
      });
    });
  }

  removeSeleccionado(controlName: string, id: number) {
    const control = this.creacion.get(controlName);
    if (control) {
      const current = control.value as number[];
      control.setValue(current.filter(x => x !== id));
    }
  }

  getNombreEspecialidad(id: number): string {
    return this.especialidades.find(e => e.especialidadPk === id)?.xnombre || 'N/A';
  }

  getNombreJerarquia(id: number): string {
    return this.jerarquias.find(j => j.jerarquiaPk === id)?.xnombre || 'N/A';
  }

  getNombreEje(id: number): string {
    return this.ejes.find(e => e.ejePk === id)?.xnombre || 'N/A';
  }

  getNombreEtiqueta(id: number): string {
    return this.etiquetas.find(et => et.etiquetaPk === id)?.xnombre || 'N/A';
  }

  getNombrePersonal(id: number): string {
    const p = this.personal.find(pe => pe.personalPk === id);
    return p ? `${p.xnombre} ${p.xapellido}` : 'N/A';
  }

  onSubmit(): void {
    if (this.creacion.valid) {
      this.proyectoService.updateProyecto(this.creacion.value).subscribe(() => {
        this.router.navigate(['/lista-proyectos']);
      });
    }
  }
}