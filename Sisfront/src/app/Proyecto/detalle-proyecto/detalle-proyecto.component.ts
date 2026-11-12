import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { HttpClient } from '@angular/common/http';

// Services & Models
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
  existePdf: boolean = false;

  constructor(
    private route: ActivatedRoute, 
    private proyectoService: ProyectoService,
    private location: Location,
    private http: HttpClient
  ) {}
  
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const proyectopkParam = params.get('proyectopk');
      if (proyectopkParam) {
        this.proyectopk = parseInt(proyectopkParam, 10);
        this.proyectoService.getProyectoById(this.proyectopk).subscribe(data => {
          this.proyecto = data;
          if (this.proyecto && this.proyecto.xnombreProyecto) {
            this.verificarExistenciaPdf(this.proyecto.xnombreProyecto);
          }
        });
      } else {
        console.error('El parámetro proyectopk no está presente');
      }
    });
  }

  /**
   * Verifica de forma silenciosa mediante una petición HEAD si el PDF existe en assets/archivoproyectos/.
   */
  private verificarExistenciaPdf(nombreProyecto: string): void {
    this.existePdf = false;
    const pdfUrl = `${this.archivosProyectosPath}${nombreProyecto}.pdf`;

    this.http.head(pdfUrl, { observe: 'response' }).subscribe({
      next: (response) => {
        const contentType = response.headers.get('content-type');
        if (response.status === 200 && contentType && contentType.includes('application/pdf')) {
          this.existePdf = true;
        } else {
          this.existePdf = false;
        }
      },
      error: () => {
        this.existePdf = false;
      }
    });
  }

  volver(): void {
    this.location.back();
  }
}