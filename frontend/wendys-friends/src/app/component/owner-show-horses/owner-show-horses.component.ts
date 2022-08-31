import {Component, Inject, OnInit} from '@angular/core';
import {Horse} from "../../dto/horse";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {OwnerDialogComponent} from "../owner-dialog/owner-dialog.component";
import {HorseService} from "../../service/horse.service";
import {OwnerService} from "../../service/owner.service";

@Component({
  selector: 'app-owner-show-horses',
  templateUrl: './owner-show-horses.component.html',
  styleUrls: ['./owner-show-horses.component.scss']
})
export class OwnerShowHorsesComponent implements OnInit {

  error = false;
  errorMessage = '';
  horses:  Horse[];
  ownerId: number;

  constructor(public dialogRef: MatDialogRef<OwnerDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private horseService: HorseService, private ownerService: OwnerService) {
    this.ownerId = data.ownerId;
  }

  ngOnInit(): void {
    this.getHorsesByOwnerId(this.ownerId);
  }

  /**
   * Loads all horses for the specified owner-id
   * @param id the id of the owner
   */
  getHorsesByOwnerId(id: number) {
    this.ownerService.getHorsesByOwnerId(id).subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
        console.log(horses);
      },
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
      this.errorMessage = error.error.message;
    }
  }

}
