import { Component, OnInit } from '@angular/core';
import { Corte } from '../../models/corte';
import { CorteService } from '../../service/corte.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-cortes',
  templateUrl: './lista-cortes.component.html',
  styleUrls: ['./lista-cortes.component.css']
})
export class ListaCortesComponent implements OnInit{

  cortes: Corte[];
  searchText: string = '';
  fotoUrls: { [key: string]: string } = {
    'CSJ AMAZONAS': './assets/cortesimg/csjamazonas.jpg',
    'CSJ ANCASH': 'assets/cortesimg/csjancash.jpg',
    'CSJ APURIMAC': 'assets/cortesimg/csjapurimac.jpg',
    'CSJ AREQUIPA': './assets/cortesimg/csjarequipa.jpg',
    'CSJ AYACUCHO': 'assets/cortesimg/csjayacucho.jpg',
    'CSJ CAJAMARCA': './assets/cortesimg/csjcajamarca.jpg',
    'CSJ CALLAO': 'assets/cortesimg/csjcallao.jpg',
    'CSJ CAÑETE': 'assets/cortesimg/csjcanete.jpg',
    'CSJ CUSCO': 'assets/cortesimg/csjcusco.jpg',
    'CSJ EL SANTA': 'assets/cortesimg/csjelsanta.jpg',
    'CSJ HUANCAVELICA': 'assets/cortesimg/csjhuancavelica.jpg',
    'CSJ HUÁNUCO': 'assets/cortesimg/csjhuanuco.jpg',
    'CSJ HUAURA': 'assets/cortesimg/csjhuaura.jpg',
    'CSJ ICA': 'assets/cortesimg/csjica.jpg',
    'CSJ JUNÍN': 'assets/cortesimg/csjjunin.jpg',
    'CSJ LA LIBERTAD': 'assets/cortesimg/csjlalibertad.jpg',
    'CSJ LAMBAYEQUE': 'assets/cortesimg/csjlambayeque.jpg',
    'CSJ LIMA': 'assets/cortesimg/csjlima.jpg',
    'CSJ LIMA ESTE': 'assets/cortesimg/csjlimaeste.jpg',
    'CSJ LIMA NORTE': './assets/cortesimg/csjlimanorte.jpg',
    'CSJ LIMA SUR': 'assets/cortesimg/csjlimasur.jpg',
    'CSJ LORETO': 'assets/cortesimg/csjloreto.jpg',
    'CSJ MADRE DE DIOS': 'assets/cortesimg/csjmadrededios.jpg',
    'CSJ MOQUEGUA': 'assets/cortesimg/csjmoquegua.jpg',
    'CSJ PASCO': 'assets/cortesimg/csjpasco.jpg',
    'CSJ PIURA': 'assets/cortesimg/csjpiura.jpg',
    'CSJ PUENTE PIEDRA VENTANILLA': 'assets/cortesimg/csjpuentepiedraventanilla.jpg',
    'CSJ PUNO': 'assets/cortesimg/csjpuno.jpg',
    'CSJ SAN MARTÍN': 'assets/cortesimg/csjsanmartin.jpg',
    'CSJ SELVA CENTRAL': 'assets/cortesimg/csjselvacentral.jpg',
    'CSJ SULLANA': 'assets/cortesimg/csjsullana.jpg',
    'CSJ TACNA': 'assets/cortesimg/csjtacna.jpg',
    'CSJ TUMBES': 'assets/cortesimg/csjtumbes.jpg',
    'CSJ UCAYALI': 'assets/cortesimg/csjucayali.jpg',
    'CSNJ PENAL ESPECIALIZADA': 'assets/cortesimg/csnjpenalespecializada.jpg'
  };

  constructor(private corteServicie: CorteService, private router: Router){}

  ngOnInit(): void {
    this.getCortes();

    }

    private getCortes(){
      this.corteServicie.getAllCortes().subscribe(dato =>{
        this.cortes=dato.map(corte=>{
          return{
            ...corte,
            fotoUrl:this.fotoUrls[corte.xnombreCorto]
          }
        }).sort((a, b) => a.xnombreCorto.localeCompare(b.xnombreCorto)); // Ordenar por xnombrecorto
      });
    }

    filteredCortes() {
      if (!this.searchText) {
        return this.cortes;
      }
      const searchTextLower = this.searchText.toLowerCase();
      return this.cortes.filter(corte =>
        corte.xnombre.toLowerCase().includes(searchTextLower) ||
        corte.xnombreCorto.toLowerCase().includes(searchTextLower)
      );
    }

    viewProyectos(xnombreCorto: string): void {
      this.router.navigate(['/detalle-corteproyecto'], { queryParams: { filtro: xnombreCorto } });
    }

}
