import { Component, ElementRef, ViewChild, AfterViewInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-rag',
  templateUrl: './rag.component.html',
  styleUrls: ['./rag.component.css']
})
export class RagComponent implements AfterViewInit {
  @ViewChild('messagesEnd') messagesEnd!: ElementRef;
  @ViewChild('messagesArea') messagesArea!: ElementRef;

  messages: { role: 'user' | 'bot'; text?: string; items?: string[] }[] = [];
  inputText = '';
  loading = false;
  open = false;
  endpoint = 'http://localhost:8080/sis/Rag/query';

  constructor(private http: HttpClient) {}

  ngAfterViewInit(): void {
    this.scrollToBottom();
  }

  /**
   * Alternar estado de apertura del chat
   */
  toggleChat(): void {
    this.open = !this.open;
    if (this.open) {
      this.scrollToBottom();
    }
  }

  sendMessage(): void {
    const text = this.inputText.trim();
    if (!text || this.loading) return;

    // Agregar mensaje del usuario
    this.messages.push({ role: 'user', text });
    this.inputText = '';
    this.loading = true;
    this.scrollToBottom();

    const url = `${this.endpoint}?question=${encodeURIComponent(text)}`;
    
    this.http.get(url, { responseType: 'text' }).subscribe({
      next: (resp) => {
        const botText = (resp || 'No se obtuvo una respuesta válida del asistente.').toString();
        
        // Detectar bloques de proyectos en la respuesta del backend
        const projectRegex = /- Nombre del Proyecto:[\s\S]*?(?=(?:- Nombre del Proyecto:)|$)/g;
        const matches = botText.match(projectRegex);

        if (matches && matches.length > 0) {
          const items = matches.map(m => m.replace(/^-/, '').trim());
          this.messages.push({ role: 'bot', items });
        } else {
          this.messages.push({ role: 'bot', text: botText });
        }
        
        this.loading = false;
        this.scrollToBottom();
      },
      error: () => {
        this.messages.push({ 
          role: 'bot', 
          text: 'Disculpe, ocurrió un problema al conectar con el servicio de Inteligencia Artificial (RAG).' 
        });
        this.loading = false;
        this.scrollToBottom();
      }
    });
  }

  private scrollToBottom(): void {
    setTimeout(() => {
      try {
        if (this.messagesArea?.nativeElement) {
          this.messagesArea.nativeElement.scrollTop = this.messagesArea.nativeElement.scrollHeight;
        } else if (this.messagesEnd?.nativeElement) {
          this.messagesEnd.nativeElement.scrollIntoView({ behavior: 'smooth' });
        }
      } catch (e) {}
    }, 50);
  }
}