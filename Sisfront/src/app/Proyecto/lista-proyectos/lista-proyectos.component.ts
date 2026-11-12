import { Component, OnInit } from '@angular/core';
import { Proyecto } from '../../models/proyecto';
import { ProyectoService } from '../../service/proyecto.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-lista-proyectos',
  templateUrl: './lista-proyectos.component.html',
  styleUrls: ['./lista-proyectos.component.css'],
})
export class ListaProyectosComponent implements OnInit {
  proyectos: Proyecto[];
  page: number = 1;
  noOfRows: number = 10;
  datas: any;
  searchText: string = '';

  constructor(
    private proyectoService: ProyectoService, 
    private route: ActivatedRoute, 
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const filtro = params['filtro'];
      this.getProyectos(filtro);
    });
  }

  filteredProyectos() {
    if (!this.searchText) {
      return this.proyectos;
    }
    const searchTextLower = this.searchText.toLowerCase();
    return this.proyectos.filter(proyecto => {
      return (proyecto.xnombreProyecto && proyecto.xnombreProyecto.toLowerCase().includes(searchTextLower)) ||
             (proyecto.xobjetivoGeneral && proyecto.xobjetivoGeneral.toLowerCase().includes(searchTextLower)) ||
             (proyecto.nombreCorte && proyecto.nombreCorte.toLowerCase().includes(searchTextLower)) ||
             (proyecto.nombreEstado && proyecto.nombreEstado.toLowerCase().includes(searchTextLower));
    });
  }
  
  private getProyectos(filtro?: string): void {
    this.proyectoService.getAllProyectos().subscribe(data => {
      this.proyectos = data;
      this.datas = data;
      if (filtro) {
        this.proyectos = this.proyectos.filter(p => p.nombreCorte === filtro);
      }
    });
  }
  
  getStartIndex(currentPage: number, lastPage: number) {
    let firstIndex = 1;
    if ((currentPage !== lastPage) || (currentPage > 0 && lastPage > 0)) {
      firstIndex = (Number(this.noOfRows) * (Number(currentPage) - 1) + 1);
    }
    return firstIndex.toString();
  }

  getLastIndex(currentPage: number, lastPage: number): string {
    if (!this.datas || this.datas.length === 0) {
      return '0';
    }
    let lastIndex = (Number(this.noOfRows) * (Number(currentPage)));
    if (lastIndex > this.datas.length) {
      lastIndex = this.datas.length;
    }
    return lastIndex.toString();
  }

  verDetalle(proyectopk: number): void {
    this.router.navigate(['/detalle-proyecto', proyectopk]);
  }

  buscar() {
    this.page = 1;
  }
}