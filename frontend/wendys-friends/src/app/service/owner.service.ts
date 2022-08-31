import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Owner} from '../dto/owner';
import {Horse} from "../dto/horse";

@Injectable({
  providedIn: 'root'
})
export class OwnerService {

  private messageBaseUri: string = this.globals.backendUri + '/owners';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  /**
   * Loads specific owner from the backend
   * @param id of owner to load
   */
  getOwnerById(id: number): Observable<Owner> {
    console.log('Load owner details for ' + id);
    return this.httpClient.get<Owner>(this.messageBaseUri + '/' + id);
  }

  /**
   * Saves owner
   * @param owner the owner that gets saved
   */
  saveOwner(owner: Owner): Observable<Owner>{
    console.log('Saving owner');
    return this.httpClient.post<Owner>(this.messageBaseUri, owner);
  }

  /**
   * Loads all owners
   */
  public getAllOwner(): Observable<Owner[]> {
    console.log('Load all owner');
    return this.httpClient.get<Owner[]>(this.messageBaseUri);
  }

  /**
   * Updates owner
   * @param owner the owner that gets updated
   */
  updateOwner(owner: Owner): Observable<Owner> {
    console.log('Update Owner');
    return this.httpClient.put<Owner>(this.messageBaseUri + '/' + owner.id, owner);
  }

  /**
   * Deletes owner by id
   * @param id the id of the owner that gets deleted
   **/
  deleteOwnerById(id: number): Observable<{}> {
    console.log('Delete Owner');
    return this.httpClient.delete<Owner>(this.messageBaseUri + '/' + id);
  }

  /**
   * Loads all owners with the parameter name in their name
   * @param name the name of the owner that get searched
   */
  public searchOwnerByName(name: String): Observable<Owner[]> {
    console.log('Search owner');
    console.log(this.messageBaseUri + '/namesearch/' + name);
    return this.httpClient.get<Owner[]>(this.messageBaseUri + '/namesearch/' + name);
  }

  /**
   * Loads owner by owner-name
   * @param name the name of the owner to load
   */
  public getOwnerByName(name: String): Observable<Owner> {
    console.log('Get owner ' + name);
    var text = '' + name;
    var url = this.messageBaseUri + '/name/' + encodeURI(text);
    console.log('URL encoded: ' + url);
    return this.httpClient.get<Owner>(url);
  }

  /**
   * Loads horses that correspond to owner-id
   * @param id the id of the owner
   */
  public getHorsesByOwnerId(id: number): Observable<Horse[]> {
    console.log('Get horses with ownerId ' + id);
    var url = this.globals.backendUri + 'horses/ownerid/' + id;
    return this.httpClient.get<Horse[]>(url);
  }

}
