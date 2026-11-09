import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-estadistica',
  templateUrl: './estadistica.component.html',
  styleUrls: ['./estadistica.component.css']
})
export class EstadisticaComponent implements OnInit {
  // URL limpia y desinfectada para la carga en iframe
  iframeUrl: SafeResourceUrl | null = null;
  
  // URL cruda de Power BI
  private readonly rawPowerBiUrl: string = 
    'https://app.powerbi.com/view?r=eyJrIjoiNjRiNGE3ZGUtYjFjOS00OGQ3LTg2NDctMGNhNDhhNjkwMDViIiwidCI6IjY0NzgzMWI2LWE2MzktNDliNC05ZGY1LWIyMGU1ODZhM2UyNyJ9';

  isLoading: boolean = true;
  isFullscreen: boolean = false;

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
    // Sanitizar la URL para prevenir bloqueos de seguridad de Angular
    this.iframeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(this.rawPowerBiUrl);
  }

  /**
   * Evento al completar la carga del iframe
   */
  onIframeLoad(): void {
    this.isLoading = false;
  }

  /**
   * Alternar modo de vista en pantalla completa
   */
  toggleFullscreen(): void {
    this.isFullscreen = !this.isFullscreen;
  }

  /**
   * Forzar recarga del reporte
   */
  recargarReporte(): void {
    this.isLoading = true;
    const currentUrl = this.rawPowerBiUrl;
    this.iframeUrl = null;
    
    setTimeout(() => {
      this.iframeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(currentUrl);
    }, 100);
  }
}