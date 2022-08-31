import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {OwnerService} from "../../service/owner.service";
import {Owner} from "../../dto/owner";

@Component({
  selector: 'app-owner-dialog',
  templateUrl: './owner-dialog.component.html',
  styleUrls: ['./owner-dialog.component.scss']
})
export class OwnerDialogComponent implements OnInit {

  private owner = new Owner(null, null, null, null);
  private error = false;
  private errorMessage = '';

  constructor(public dialogRef: MatDialogRef<OwnerDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private ownerService: OwnerService) {
    this.owner.name = data.name;
    this.owner.id = data.id;
    this.owner.createdAt = data.createdAt;
    this.owner.updatedAt = data.updatedAt;
  }

  ngOnInit(): void {
  }

  /**
   * Updates owner with the specified values
   */
  save() {
    this.ownerService.updateOwner(this.owner).subscribe(result => {
      console.log('Owner updated: ' + this.owner.name);
    }, error => {
      this.defaultServiceErrorHandling(error);
    });
    this.dialogRef.close("Changes saved.");
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

