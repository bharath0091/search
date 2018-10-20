import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataShareService {
  private messageSource = new BehaviorSubject("");
  observableData = this.messageSource.asObservable();

  constructor() { }

  publishData(data: string) {
    this.messageSource.next(data);
  }
}
