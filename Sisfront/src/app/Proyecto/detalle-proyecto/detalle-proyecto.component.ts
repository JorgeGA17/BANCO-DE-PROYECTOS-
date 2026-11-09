import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ProyectoService } from '../../service/proyecto.service';
import { Proyecto } from '../../models/proyecto';

@Component({
  selector: 'app-detalle-proyecto',
  templateUrl: './detalle-proyecto.component.html',
  styleUrls: ['./detalle-proyecto.component.css']
})
export class DetalleProyectoComponent implements OnInit {
  proyecto!: Proyecto;
  proyectopk!: number;
  archivosProyectosPath = 'assets/archivoproyectos/';

  constructor(
    private route: ActivatedRoute, 
    private proyectoService: ProyectoService,
    private location: Location // Inyección directa limpia
  ) {}
  
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const proyectopkParam = params.get('proyectopk');
      if (proyectopkParam) {
        this.proyectopk = parseInt(proyectopkParam, 10);
        this.proyectoService.getProyectoById(this.proyectopk).subscribe(data => {
          this.proyecto = data;
        });
      } else {
        console.error('El parámetro proyectopk no está presente');
      }
    });
  }

  volver(): void {
    this.location.back();
  }
}