import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {HorseService} from '../../service/horse.service';
import {Horse} from '../../dto/horse';
import {Owner} from "../../dto/owner";
import {OwnerService} from "../../service/owner.service";
import {MatDialog} from "@angular/material/dialog";
import {HorseDialogComponent} from "../horse-dialog/horse-dialog.component";
import {AddHorseComponent} from "../add-horse/add-horse.component";

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss']
})

export class HorseFormComponent implements OnInit {

  error = false;
  errorMessage = '';
  horse = new Horse(null, null,null, null ,null, null, null, null, null, null);
  owner = new Owner(null, null, null, null);
  horses: Horse[];

  private nameSearchTerm: string = '';
  private descriptionSearchTerm: string = '';
  private ratingSearchTerm: string = '';
  private dateSearchTerm: string = '';
  private breedSearchTerm: string = '';

  constructor(private route: ActivatedRoute, private ownerService: OwnerService, private router: Router, private horseService: HorseService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.getAllHorses();
  }

  vanishError() {
    this.error = false;
  }

  /**
   * Opens dialog AddHorseComponent to save new horse. Reloads page after being closed
   */
  openAddHorseDialog() {
    const dialogRef = this.dialog.open(AddHorseComponent, {
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getAllHorses();
    });
  }

  /**
   * Loads all horses
   */
  getAllHorses() {
    this.horseService.getAllHorses().subscribe(
      (horses: Horse[]) => {
        this.horses = horses;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  /**
   * Opens dialog HorseDialogComponent to edit horse-data. Reloads page after being closed
   * @param horse the horse that gets edited
   */
  openDialog(horse: Horse): void {
    const dialogRef = this.dialog.open(HorseDialogComponent, {
      data: {
        owner: horse.ownerDto,
        id: horse.id,
        ownerId: horse.ownerId,
        name: horse.name,
        rating: horse.rating,
        birthDate: horse.birthDate,
        description: horse.description,
        breed: horse.breed,
        picture: horse.picture,
        createdAt: horse.createdAt,
        updatedAt: horse.updatedAt
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.getAllHorses();
    });
  }

  /**
   * Deletes horse from database. Reloads page afterwards
   * @param id the id of the horse that will get deleted
   */
  deleteHorse(id: number) {
    this.horseService.deleteHorseById(id).subscribe(result => {
      console.log('Horse deleted');
      this.getAllHorses();
    }, error => {
      this.defaultServiceErrorHandling(error);
    });
  }

  /**
   * Searches database for horses that match the search parameters
   * @param nameSearchTerm the search-term for name
   * @param descriptionSearchTerm the search-term for description
   * @param ratingSearchTerm the search-term for rating
   * @param dateSearchTerm the search-term for date after the horse was born
   * @param breedSearchTerm the search term for breed
   */
  searchHorses(nameSearchTerm: string, descriptionSearchTerm: string, ratingSearchTerm: string, dateSearchTerm: string, breedSearchTerm: string) {
      this.horseService.searchHorses('name=' + nameSearchTerm, 'description=' + descriptionSearchTerm, 'rating=' + ratingSearchTerm,
      'date=' + dateSearchTerm, 'breed=' + breedSearchTerm).subscribe(
        (horses: Horse[]) => {
          this.horses = horses;
          this.error = false;
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
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      console.log(error.error.message);
      this.errorMessage = error.error.message;
    }
  }
}
