import { Injectable } from "@nestjs/common"
import { InjectModel } from "@nestjs/mongoose"
import { MongoDataSource } from "apollo-datasource-mongodb"
import { Model } from "mongoose"

import { Days, Sessions } from "@bookings-backend/enums"

import {
  Reservable,
  ReservableModel,
  ReservableType,
  ReservableTypes,
} from "./reservables.model"
import { ReservableCollection, ReservableDocument } from "./reservables.schema"

@Injectable()
export class ReservablesService extends MongoDataSource<ReservableDocument> {
  constructor(
    @InjectModel(ReservableCollection.name) reservableModel: Model<ReservableDocument>,
  ) {
    super({
      modelOrCollection: reservableModel,
    })
  }

  async findById(id: string): Promise<Reservable | undefined> {
    return this.findOneById(id)
      .then(document => document ? this.toReservable(document) : undefined)
  }

  async findByType(type: ReservableType): Promise<Reservable[]> {
    const ids = await this.findIdsByType(type)
    const reservableDocuments = await this.findManyByIds(ids)
    return reservableDocuments
      .filter((e): e is Exclude<typeof e, null> => e !== null)
      .filter((e): e is Exclude<typeof e, undefined> => e !== undefined)
      .map(this.toReservable)
  }

  async findAll(ids?: string[]): Promise<Reservable[]> {
    const reservableDocuments = await this.findManyByIds(
      ids ? ids : await this.findAllIds(),
    )
    return reservableDocuments
      .filter((e): e is Exclude<typeof e, null> => e !== null)
      .filter((e): e is Exclude<typeof e, undefined> => e !== undefined)
      .map(this.toReservable)
  }

  async create(reservableModel: ReservableModel): Promise<Reservable> {
    return this.model.create(reservableModel).then(this.toReservable)
  }

  async updateById(
    id: string,
    reservableModel: ReservableModel,
  ): Promise<Reservable | undefined> {
    await this.deleteFromCacheById(id)
    await this.model.findByIdAndUpdate(id, reservableModel).exec()
    return this.findOneById(id)
      .then(document => document ? this.toReservable(document) : undefined)
  }

  async deleteById(id: string): Promise<Reservable | undefined> {
    await this.deleteFromCacheById(id)
    return this.model
      .findByIdAndDelete(id)
      .exec()
      .then((result) => {
        return result ? this.toReservable(result) : undefined
      })
  }

  private async findAllIds(): Promise<string[]> {
    const reservations = await this.model.find({}, ["_id"]).exec()
    return reservations.map((reservation) => reservation._id.toHexString())
  }

  private async findIdsByType(type: ReservableType): Promise<string[]> {
    const reservations = await this.model.find({ type }, ["_id"]).exec()
    return reservations.map((reservation) => reservation._id.toHexString())
  }

  private toReservable(reservableDocument: ReservableDocument): Reservable {
    return {
      id: reservableDocument._id.toHexString(),
      name: reservableDocument.name,
      description: reservableDocument.description,
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
      type: ReservableTypes.valueOf(reservableDocument.type)!,
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
      days: reservableDocument.days.map((day) => Days.valueOf(day)!),
      sessions: reservableDocument.sessions.map(
        // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
        (session) => Sessions.valueOf(session)!,
      ),
    }
  }
}
