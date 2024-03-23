import { Args, Mutation, Query, Resolver, ResolveReference } from "@nestjs/graphql"
import { IsNotEmpty } from "class-validator"

import { Session } from "@bookings-backend/enums"

import { Reservation, ReservationModel, ReservationPayload } from "./reservations.model"
import { ReservationsService } from "./reservations.service"

export class ReservationInput implements ReservationModel {
  @IsNotEmpty() title: string
  @IsNotEmpty() description: string
  date: Date
  @IsNotEmpty() reservableId: string
  session: Session
  @IsNotEmpty() userId: string
}

export interface ReservationRepresentation {
  __typename: "Reservation"
  id: string
}

@Resolver("Reservation")
export class ReservationsResolver {
  constructor(private readonly reservationsService: ReservationsService) {}

  @Query()
  async reservation(@Args("id") id: string): Promise<Reservation | undefined> {
    return this.reservationsService.findById(id)
  }

  @Query()
  async reservations(@Args("ids") ids?: string[]): Promise<Reservation[]> {
    return this.reservationsService.findAll(ids)
  }

  @Mutation()
  async addReservation(
    @Args("reservation") reservationInput: ReservationInput,
  ): Promise<ReservationPayload> {
    return await this.reservationsService
      .create(reservationInput)
      .then((reservation) => ({
        reservation,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @Mutation()
  async updateReservation(
    @Args("id") id: string,
    @Args("reservation") reservationInput: ReservationInput,
  ): Promise<ReservationPayload> {
    return this.reservationsService
      .updateById(id, reservationInput)
      .then((reservation) => ({
        reservation,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @Mutation()
  async deleteReservation(
    @Args("id") id: string,
  ): Promise<ReservationPayload> {
    return this.reservationsService
      .deleteById(id)
      .then((reservation) => ({
        reservation,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @ResolveReference()
  async resolveReference(
    reservationRepresentation: ReservationRepresentation,
  ): Promise<Reservation | undefined> {
    return this.reservationsService.findById(reservationRepresentation.id)
  }
}
