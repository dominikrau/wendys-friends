import {Component, Inject, OnInit} from '@angular/core';
import {Owner} from "../../dto/owner";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Horse} from "../../dto/horse";
import {HorseService} from "../../service/horse.service";
import {OwnerDialogComponent} from "../owner-dialog/owner-dialog.component";
import {OwnerService} from "../../service/owner.service";

@Component({
  selector: 'app-horse-dialog',
  templateUrl: './horse-dialog.component.html',
  styleUrls: ['./horse-dialog.component.scss']
})
export class HorseDialogComponent implements OnInit {

  error = false;
  errorMessage = '';
  horse = new Horse(null, null, null, null, null, null, null, null, null, null);

  constructor(public dialogRef: MatDialogRef<OwnerDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private horseService: HorseService, private ownerService: OwnerService) {
    this.horse.ownerDto = data.owner;
    this.horse.id = data.id;
    this.horse.ownerId = data.ownerId;
    this.horse.name = data.name;
    this.horse.rating = data.rating;
    this.horse.birthDate = data.birthDate;
    this.horse.description = data.description;
    this.horse.breed = data.breed;
    this.horse.picture = data.picture;
    this.horse.createdAt = data.createdAt;
    this.horse.updatedAt = data.updatedAt;
  }

  ngOnInit(): void {
  }

  /**
   * Gets id from corresponding owner and updates the horse with the specified values
   */
  save() {
    this.error = false;
    this.ownerService.getOwnerByName(this.horse.ownerDto.name).subscribe(
      (owner: Owner) => {
        this.horse.ownerDto = owner;
        this.horse.ownerId = owner.id;
          this.horseService.updateHorse(this.horse).subscribe(result => {
            console.log('Horse updated: ' + this.horse.name);
            }, error => {
            this.defaultServiceErrorHandling(error);
          });
      },
      error => {
        console.log("save error: ");
        this.defaultServiceErrorHandling(error);
      }
    );
  }
  /**
   * Closes current dialog
   */
  closeDialog() {
    this.dialogRef.close();
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
}
