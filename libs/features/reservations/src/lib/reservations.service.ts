import { Injectable } from "@nestjs/common"
import { InjectModel } from "@nestjs/mongoose"
import { MongoDataSource } from "apollo-datasource-mongodb"
import { Model } from "mongoose"

import { Sessions } from "@bookings-backend/enums"

import { Reservation, ReservationModel } from "./reservations.model"
import { ReservationCollection, ReservationDocument } from "./reservations.schema"

@Injectable()
export class ReservationsService extends MongoDataSource<ReservationDocument> {
  constructor(
    @InjectModel(ReservationCollection.name)
      reservationModel: Model<ReservationDocument>,
  ) {
    super({
      modelOrCollection: reservationModel,
    })
  }

  async findById(id: string): Promise<Reservation | undefined> {
    return this.findOneById(id)
      .then(document => document ? this.toReservation(document) : undefined)
  }

  async findByReservableId(reservableId: string): Promise<Array<Reservation>> {
    const ids = await this.findIdsByReservableId(reservableId)
    const reservationDocuments = await this.findManyByIds(ids)
    return reservationDocuments
      .filter((e): e is Exclude<typeof e, null> => e !== null)
      .filter((e): e is Exclude<typeof e, undefined> => e !== undefined)
      .map(this.toReservation)
  }

  async findByUserId(userId: string): Promise<Reservation[]> {
    const ids = await this.findIdsByUserId(userId)
    const reservationDocuments = await this.findManyByIds(ids)
    return reservationDocuments
      .filter((e): e is Exclude<typeof e, null> => e !== null)
      .filter((e): e is Exclude<typeof e, undefined> => e !== undefined)
      .map(this.toReservation)
  }

  async findAll(ids?: string[]): Promise<Reservation[]> {
    const reservationDocuments = await this.findManyByIds(
      ids ? ids : await this.findAllIds(),
    )
    return reservationDocuments
      .filter((e): e is Exclude<typeof e, null> => e !== null)
      .filter((e): e is Exclude<typeof e, undefined> => e !== undefined)
      .map(this.toReservation)
  }

  async create(reservationModel: ReservationModel): Promise<Reservation> {
    return this.model.create(reservationModel).then(this.toReservation)
  }

  async updateById(
    id: string,
    reservationModel: ReservationModel,
  ): Promise<Reservation | undefined> {
    await this.deleteFromCacheById(id)
    await this.model.findByIdAndUpdate(id, reservationModel).exec()
    return this.findOneById(id)
      .then(document => document ? this.toReservation(document) : undefined)
  }

  async deleteById(id: string): Promise<Reservation | undefined> {
    await this.deleteFromCacheById(id)
    return this.model
      .findByIdAndDelete(id)
      .exec()
      .then((result) => {
        return result ? this.toReservation(result) : undefined
      })
  }

  private async findAllIds(): Promise<string[]> {
    const reservations = await this.model.find({}, ["_id"]).exec()
    return reservations.map((reservation) => reservation._id.toHexString())
  }

  private async findIdsByReservableId(reservableId: string): Promise<string[]> {
    const reservations = await this.model.find({ reservableId }, ["_id"]).exec()
    return reservations.map((reservation) => reservation._id.toHexString())
  }

  private async findIdsByUserId(userId: string): Promise<string[]> {
    const reservations = await this.model.find({ userId }, ["_id"]).exec()
    return reservations.map((reservation) => reservation._id.toHexString())
  }

  private toReservation(
    reservationDocument: ReservationDocument,
  ): Reservation {
    return {
      id: reservationDocument._id.toHexString(),
      title: reservationDocument.title,
      description: reservationDocument.description,
      date: reservationDocument.date,
      reservableId: reservationDocument.reservableId.toHexString(),
      // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
      session: Sessions.valueOf(reservationDocument.session)!,
      userId: reservationDocument.userId.toHexString(),
    }
  }
}
