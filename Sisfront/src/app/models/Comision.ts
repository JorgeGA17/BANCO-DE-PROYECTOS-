import { Corte } from './corte';
import { Periodo } from './Periodo';


export class Comision {

    comisionPk:number;
    xdescripcion: string;
    corteId: number;
    nombreCorte: String;
    periodoId: Periodo;
    nombrePeriodo: string;
    xresolucion: string;

}