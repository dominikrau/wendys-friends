import {Component, OnInit} from '@angular/core';
import {OwnerService} from '../../service/owner.service';
import {Owner} from '../../dto/owner';
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {OwnerDialogComponent} from "../owner-dialog/owner-dialog.component";
import {OwnerShowHorsesComponent} from "../owner-show-horses/owner-show-horses.component";

@Component({
  selector: 'app-owner',
  templateUrl: './owner.component.html',
  styleUrls: ['./owner.component.scss']
})
export class OwnerComponent implements OnInit {

  private error = false;
  private errorMessage = '';
  private owner = new Owner(null, null, null, null);
  private owners : Owner[];
  private _searchTerm: string;

  constructor(private ownerService: OwnerService, private router: Router, private route: ActivatedRoute, public dialog: MatDialog) {
  }

  ngOnInit() {
      this.getAllOwner();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Loads the owner for the specified id
   * @param id the id of the owner
   */
  private loadOwner(id: number) {
    this.ownerService.getOwnerById(id).subscribe(
      (owner: Owner) => {
        this.owner = owner;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Loads all owners
   */
  getAllOwner() {
    this.ownerService.getAllOwner().subscribe(
      (owner: Owner[]) => {
        this.owners = owner;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Saves the owner with the specified values in user-form
   */
  onSubmit() {
    this.ownerService.saveOwner(this.owner).subscribe(result => {
      console.log('Owner saved: ' + this.owner.name);
      this.getAllOwner();
    }, error => {
      this.defaultServiceErrorHandling(error);
    });
  }

  /**
   * Opens dialog OwnerDialogComponent to edit owner-data. Reloads page after being closed
   * @param owner the owner-object that will get edited
   */
  openDialog(owner: Owner): void {
    const dialogRef = this.dialog.open(OwnerDialogComponent, {
      data: {
        id: owner.id,
        name: owner.name,
        createdAt: owner.createdAt,
        updatedAt: owner.updatedAt
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getAllOwner();
    });
  }

  /**
   * Deletes owner by owner-id, if no horse is associated with this owner. Reloads page afterwards
   * @param id the owner-id of the owner that will get deleted
   */
  deleteOwner(id: number) {
    this.ownerService.deleteOwnerById(id).subscribe(result => {
      console.log('Owner deleted');
      this.getAllOwner();
    }, error => {
      this.defaultServiceErrorHandling(error);
    });
  }

  /**
   * Gets the term from owner search
   */
  get searchTerm(): string {
    this.error = false;
    return this._searchTerm;
  }

  /**
   * Sets the term from owner search and loads all owner associated with the value
   * If parameter is not set, loads all owners
   * @param value part of the name from owner that gets searched
   */
  set searchTerm(value: string) {
    this._searchTerm = value;
    // got input in search bar
    if (this._searchTerm != '') {
      this.ownerService.searchOwnerByName(this._searchTerm).subscribe(
        (owner: Owner[]) => {
          this.owners = owner;
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
      );
    }
    // search bar has no input
    else {
      this.getAllOwner();
    }
  }

  /**
   * Opens dialog to display all horses associated with the corresponding owner, reloads after being closed
   * @param id the corresponding owner-id
   */
  openHorsesDialog(id: number): void {
    const dialogRef = this.dialog.open(OwnerShowHorsesComponent, {
      data: {
        ownerId: id,
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getAllOwner();
    });
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
