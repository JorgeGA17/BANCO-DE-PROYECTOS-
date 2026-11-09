import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxPaginationModule } from 'ngx-pagination';

// Service & Models
import { ProyectoService } from '../../service/proyecto.service';
import { Proyecto } from '../../models/proyecto';

// Angular Material Modules
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-editar',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NgxPaginationModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent implements OnInit {
  proyectos: Proyecto[] = [];
  page: number = 1;
  noOfRows: number = 10;
  datas: any;
  searchText: string = '';

  constructor(
    private proyectoService: ProyectoService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const filtro = params['filtro'];
      this.getProyectos(filtro);
    });
  }

  filteredProyectos() {
    if (!this.searchText) {
      return this.proyectos;
    }
    const searchTextLower = this.searchText.toLowerCase();
    return this.proyectos.filter((proyecto) => {
      return (
        (proyecto.xnombreProyecto &&
          proyecto.xnombreProyecto.toLowerCase().includes(searchTextLower)) ||
        (proyecto.nombreCorte &&
          proyecto.nombreCorte.toLowerCase().includes(searchTextLower))
      );
    });
  }

  private getProyectos(filtro?: string): void {
    this.proyectoService.getAllProyectos().subscribe((data) => {
      this.proyectos = data.sort((a, b) => a.proyectoPk - b.proyectoPk);
      this.datas = this.proyectos;

      if (filtro) {
        this.proyectos = this.proyectos.filter(
          (p) => p.nombreCorte === filtro
        );
      }
    });
  }

  getStartIndex(currentPage: number, lastPage: number) {
    let firstIndex = 1;
    if (currentPage !== lastPage || (currentPage > 0 && lastPage > 0)) {
      firstIndex = Number(this.noOfRows) * (Number(currentPage) - 1) + 1;
    }
    return firstIndex.toString();
  }

  getLastIndex(currentPage: number, lastPage: number): string {
    if (!this.datas || this.datas.length === 0) {
      return '0';
    }
    let lastIndex = Number(this.noOfRows) * Number(currentPage);
    if (lastIndex > this.datas.length) {
      lastIndex = this.datas.length;
    }
    return lastIndex.toString();
  }

  editarProyecto(id: number): void {
    this.router.navigate(['/editar-proyecto', id]);
  }

  buscar() {
    this.page = 1;
  }
}