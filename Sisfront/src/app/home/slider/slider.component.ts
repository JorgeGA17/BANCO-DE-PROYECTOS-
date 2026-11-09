import { Component, OnInit, OnDestroy } from '@angular/core';

interface Slide {
  image: string;
  title: string;
  subtitle: string;
}

@Component({
  selector: 'app-slider',
  templateUrl: './slider.component.html',
  styleUrls: ['./slider.component.css']
})
export class SliderComponent implements OnInit, OnDestroy {
  slides: Slide[] = [
    {
      image: 'assets/carrusel/palacio1.jpg',
      title: 'Palacio Nacional de Justicia',
      subtitle: 'Sede principal del Poder Judicial de la República del Perú'
    },
    {
      image: 'assets/carrusel/palacio5.jpg',
      title: 'Modernización del Despacho Judicial',
      subtitle: 'Impulsando la transformación digital en la gestión jurisdiccional'
    },
    {
      image: 'assets/carrusel/ministra.jpg',
      title: 'Compromiso e Institucionalidad',
      subtitle: 'Fortaleciendo el servicio de justicia para toda la ciudadanía'
    }
  ];

  currentSlide = 0;
  private autoPlayInterval: any;

  ngOnInit(): void {
    this.startAutoPlay();
  }

  ngOnDestroy(): void {
    this.stopAutoPlay();
  }

  startAutoPlay(): void {
    this.autoPlayInterval = setInterval(() => {
      this.nextSlide();
    }, 6000);
  }

  stopAutoPlay(): void {
    if (this.autoPlayInterval) {
      clearInterval(this.autoPlayInterval);
    }
  }

  nextSlide(): void {
    this.currentSlide = (this.currentSlide + 1) % this.slides.length;
  }

  prevSlide(): void {
    this.currentSlide = (this.currentSlide - 1 + this.slides.length) % this.slides.length;
  }

  goToSlide(index: number): void {
    this.currentSlide = index;
    this.stopAutoPlay();
    this.startAutoPlay();
  }
}