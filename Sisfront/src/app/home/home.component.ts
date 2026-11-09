import { Component } from '@angular/core';

export interface MiembroUGDJ {
  nombre: string;
  cargo: string;
}

const CONFORMACION_DATA: MiembroUGDJ[] = [
  { nombre: 'Dr. Omar Vladimir Sumaria Benavente', cargo: 'Jefe de la UGDJ' },
  { nombre: 'Ing. John Lico Jimenez Casaverde', cargo: 'Gestor Administrativo' },
  { nombre: 'Abog. Katherine Cordova', cargo: 'Coordinadora de Proyectos' },
  { nombre: 'Abog. Anggie Leon', cargo: 'Coordinadora Normativo' },
  { nombre: 'Ing. Raul Melgarejo Teran', cargo: 'Analista Informático' },
  { nombre: 'Lic. Bebeto Juan Pizarro Marcos', cargo: 'Analista Estadístico' },
  { nombre: 'Abog. Silvia Milagros Morales Silva', cargo: 'Asistente Administrativo II' },
  { nombre: 'Ing. Jorge Alfredo García Alejo', cargo: 'Asistente Administrativo II' },
  { nombre: 'Br. David Ataucuri Rodríguez', cargo: 'Asistente Administrativo I' }
];

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  displayedColumns: string[] = ['nombre', 'cargo'];
  dataSource = CONFORMACION_DATA;
}