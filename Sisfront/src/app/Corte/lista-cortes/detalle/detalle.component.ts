import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';
import { MiembroService } from '../../../service/miembro.service';
import { ProyectoService } from '../../../service/proyecto.service';
import { PresidenteService } from '../../../service/presidente.service';
// import { ComisionService } from '../../../service/comision.service'; // Se eliminó esta importación
import { Proyecto } from '../../../models/proyecto';
import { Presidente } from '../../../models/Presidente';
import { Miembro } from '../../../models/Miembro';
// import { Comision } from '../../../models/Comision'; // Se eliminó esta importación

const fotoUrls: { [key: string]: string } = {
  'CSJ AMAZONAS': 'assets/presidentes/csjamazonas.png',
  'CSJ ANCASH': 'assets/presidentes/csjancash.jpg',
  'CSJ APURIMAC': 'assets/presidentes/csjapurimac.jpg',
  'CSJ AREQUIPA': 'assets/presidentes/csjarequipa.jpg',
  'CSJ AYACUCHO': 'assets/presidentes/csjayacucho.jpg',
  'CSJ CAJAMARCA': 'assets/presidentes/csjcajamarca.jpg',
  'CSJ CALLAO': 'assets/presidentes/csjcallao.jpg',
  'CSJ CAÑETE': 'assets/presidentes/csjcanete.jpg',
  'CSJ CUSCO': 'assets/presidentes/csjcusco.jpg',
  'CSJ EL SANTA': 'assets/presidentes/csjelsanta.jpg',
  'CSJ HUANCAVELICA': 'assets/presidentes/csjhuancavelica.JPG',
  'CSJ HUÁNUCO': 'assets/presidentes/csjhuanuco.jpg',
  'CSJ HUAURA': 'assets/presidentes/csjhuaura.jpg',
  'CSJ ICA': 'assets/presidentes/csjica.jpg',
  'CSJ JUNÍN': 'assets/presidentes/csjjunin.jpg',
  'CSJ LA LIBERTAD': 'assets/presidentes/csjlalibertad.jpg',
  'CSJ LAMBAYEQUE': 'assets/presidentes/csjlambayeque.jpg',
  'CSJ LIMA': 'assets/presidentes/csjlima.jpg',
  'CSJ LIMA ESTE': 'assets/presidentes/csjlimaeste.jpg',
  'CSJ LIMA NORTE': 'assets/presidentes/csjlimanorte.jpg',
  'CSJ LIMA SUR': 'assets/presidentes/csjlimasur.jpg',
  'CSJ LORETO': 'assets/presidentes/csjloreto.jpg',
  'CSJ MADRE DE DIOS': 'assets/presidentes/csjmadrededios.jpg',
  'CSJ MOQUEGUA': 'assets/presidentes/csjmoquegua.jpg',
  'CSJ PASCO': 'assets/presidentes/csjpasco.jpg',
  'CSJ PIURA': 'assets/presidentes/csjpiura.jpg',
  'CSJ PUENTE PIEDRA VENTANILLA': 'assets/presidentes/csjpuentepiedraventanilla.jpg',
  'CSJ PUNO': 'assets/presidentes/csjpuno.jpg',
  'CSJ SAN MARTÍN': 'assets/presidentes/csjsanmartin.jpg',
  'CSJ SELVA CENTRAL': 'assets/presidentes/csjselvacentral.jpg',
  'CSJ SULLANA': 'assets/presidentes/csjsullana.jpg',
  'CSJ TACNA': 'assets/presidentes/csjtacna.jpg',
  'CSJ TUMBES': 'assets/presidentes/csjtumbes.jpg',
  'CSJ UCAYALI': 'assets/presidentes/csjucayali.jpg',
  'CSNJ PENAL ESPECIALIZADA': 'assets/presidentes/csnjpenalespecializada.jpg'
};

@Component({
  selector: 'app-detalle',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {
  miembros: Miembro[] = [];
  proyectos: Proyecto[] = [];
  presidentes: Presidente[] = [];
  filtro: string;
  archivosComisionPath = 'assets/archivocomision/';

  constructor(
    private miembroService: MiembroService,
    private proyectoService: ProyectoService,
    private presidenteService: PresidenteService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.filtro = params['filtro'];
      this.loadAllData();
    });
  }

  loadAllData(): void {
    forkJoin({
      proyectos: this.proyectoService.getAllProyectos(),
      miembros: this.miembroService.getAllMiembros(),
      presidentes: this.presidenteService.getAllPresidentes()
    }).subscribe(({ proyectos, miembros, presidentes }) => {
      const presidentesConFoto = presidentes.map(p => ({
        ...p,
        fotoUrl: fotoUrls[p.corteNombre.trim().toUpperCase()] || ''
      }));

      // Aplicar filtros a los datos
      const filtroNormalizado = this.filtro.trim().toLowerCase();
      this.proyectos = proyectos.filter(p => {
        // Registro para cada proyecto durante el proceso de filtrado
        const nombreCorteNormalizado = p.nombreCorte.trim().toLowerCase();
        const match = nombreCorteNormalizado === filtroNormalizado;
        return match;
      });
      this.miembros = miembros.filter(m => m.comisionNombreCorte === this.filtro);
      this.presidentes = presidentesConFoto.filter(p => p.corteNombre.trim().toLowerCase() === this.filtro.trim().toLowerCase());
    });
  }


  getArchivoComisionUrl(filtro: string): string {
    const archivoNombre = `RES-${filtro.toUpperCase().replace(/\s+/g, '')}`;
    return `${this.archivosComisionPath}${archivoNombre}.pdf`;
  }
}