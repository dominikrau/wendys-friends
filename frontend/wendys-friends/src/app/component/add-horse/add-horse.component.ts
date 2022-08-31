import { Component, OnInit } from '@angular/core';
import {Owner} from "../../dto/owner";
import {Horse} from "../../dto/horse";
import {ActivatedRoute, Router} from "@angular/router";
import {OwnerService} from "../../service/owner.service";
import {HorseService} from "../../service/horse.service";

@Component({
  selector: 'app-add-horse',
  templateUrl: './add-horse.component.html',
  styleUrls: ['./add-horse.component.scss']
})
export class AddHorseComponent implements OnInit {

  error = false;
  errorMessage = '';
  horse = new Horse(null, null,null, null ,null, null, null, null, null, null);
  owner = new Owner(null, null, null, null);
  private selectedFile: File = null;

  constructor(private route: ActivatedRoute, private ownerService: OwnerService, private router: Router, private horseService: HorseService,) { }

  ngOnInit(): void {
  }

  /**
   * Reloads Page
   */
  refresh(): void {
    window.location.reload();
  }

  vanishError() {
    this.error = false;
  }

  /**
   * Saves the horse with the specified values. Gets id from corresponding owner. Reloads page afterwards
   */
  onSubmit() {
    this.ownerService.getOwnerByName(this.owner.name).subscribe(
      (owner: Owner) => {
        this.horse.ownerId = owner.id;
        this.horse.ownerDto = owner;
        this.horseService.save(this.horse).subscribe(res => {
            console.log(res);
            this.refresh();
          }, error => {
            this.defaultServiceErrorHandling(error);
          }
        );},
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      console.log(error.error.message);
      this.errorMessage = error.error.message;
    }
  }

  onFileSelected(event) {
    console.log(event);
    this.selectedFile = <File>event.target.files[0];
    this.horse.picture = this.selectedFile;
  }

  /*
  onSubmit() {
    console.log('This owner name: ' + this.owner.name);
    this.ownerService.getOwnerByName(this.owner.name).subscribe(
      (owner: Owner) => {
        this.horse.ownerId = owner.id;
        this.horse.ownerDto = owner;
        console.log('Horse owner id: ' + this.horse.ownerId);

        console.log("stringify: " + JSON.stringify(this.horse))
        let formData = new FormData();
        formData.append('HorseDto', JSON.stringify(this.horse));
        formData.append('picture', this.selectedFile);
        console.log(formData);
        let params = new HttpParams();
        const options = {
          params: params,
          reportProgress: true,
        };

        this.horseService.saveData(formData).subscribe(res => {
            console.log(res);
          }, error => {
            this.defaultServiceErrorHandling(error);
          }
        );
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  */

}
