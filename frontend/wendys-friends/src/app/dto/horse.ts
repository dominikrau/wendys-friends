import {Owner} from "./owner";

export class Horse {
  constructor(
    public id: number,
    public ownerDto: Owner,
    public ownerId: number,
    public name: string,
    public rating: number,
    public birthDate: string,
    public createdAt: string,
    public updatedAt: string,
    public breed: string,
    public picture: File,
    //public picture = new Uint8Array(1024*1024*8),
    public description?: string
  ) { }
}
