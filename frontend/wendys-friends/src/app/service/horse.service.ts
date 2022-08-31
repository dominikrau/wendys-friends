import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Horse} from '../dto/horse';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  private messageBaseUri: string = this.globals.backendUri + '/horses';
  horse: Horse;

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads all horses from the backend
   */
  public getAllHorses(): Observable<Horse[]> {
    console.log('Load all horses');
    return this.httpClient.get<Horse[]>(this.messageBaseUri);
  }

  /**
   * Loads horse by id
   * @param id the id of the horse that gets loaded
   */
  public getHorseById(id: number): Observable<Horse> {
    console.log('Load owner details for ' + id);
    return this.httpClient.get<Horse>(this.messageBaseUri + '/' + id);
  }

  /**
   * Saves horse
   * @param horse the horse that gets saved
   */
  public save(horse: Horse) {
    console.log('Save horse ' + horse);
    return this.httpClient.post<Horse>(this.messageBaseUri, horse);
  }

  /**
   * Updates horse
   * @param horse the horse object that gets updated
   */
  updateHorse(horse: Horse): Observable<Horse> {
    console.log('Update horse ' + horse.name);
    return this.httpClient.put<Horse>(this.messageBaseUri + '/' + horse.ownerId, horse);
  }

  /**
   * Deletes horse by id
   * @param id the id of the owner that gets deleted
   **/
  deleteHorseById(id: number): Observable<{}> {
    console.log('Delete Horse');
    return this.httpClient.delete<Horse>(this.messageBaseUri + '/' + id);
  }

  /**
   * Loads all owners with the parameter name in their name
   * @param name the name of the horse that get searched
   * @param description the description of the horse that get searched
   * @param rating the rating of the horse that get searched
   * @param date the date of the horse that get searched, return horses born before this date
   * @param breed the breed of the horse that get searched
   */
  public searchHorses(name: String, description: String, rating: string, date: string, breed: string): Observable<Horse[]> {
    var text = '' + name + '&' + description + '&' + rating + '&' + date + '&' + breed;
    var url = this.messageBaseUri + '/search?' + encodeURI(text);
    return this.httpClient.get<Horse[]>(url);
  }
}
