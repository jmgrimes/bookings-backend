import { Args, Mutation, Query, Resolver, ResolveReference } from "@nestjs/graphql"
import { IsNotEmpty } from "class-validator"

import { Day, Session } from "@bookings-backend/enums"

import { Reservable, ReservableModel, ReservablePayload, ReservableType } from "./reservables.model"
import { ReservablesService } from "./reservables.service"


export class ReservableInput implements ReservableModel {
  @IsNotEmpty() name: string
  @IsNotEmpty() description: string
  @IsNotEmpty() type: ReservableType
  @IsNotEmpty() days: Array<Day>
  @IsNotEmpty() sessions: Array<Session>
}

export interface ReservableReference {
  __typename: "Reservable"
  id: string
}

@Resolver("Reservable")
export class ReservablesResolver {
  constructor(private readonly reservablesService: ReservablesService) {}

  @Query()
  async reservable(@Args("id") id: string): Promise<Reservable | undefined> {
    return this.reservablesService.findById(id)
  }

  @Query()
  async reservables(@Args("ids") ids?: string[]): Promise<Reservable[]> {
    return this.reservablesService.findAll(ids)
  }

  @Query()
  async reservablesByType(
    @Args("type") type: ReservableType,
  ): Promise<Reservable[]> {
    return this.reservablesService.findByType(type)
  }

  @Mutation()
  async addReservable(
    @Args("reservable") reservableInput: ReservableInput,
  ): Promise<ReservablePayload> {
    return this.reservablesService
      .create(reservableInput)
      .then((reservable) => ({
        reservable,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @Mutation()
  async updateReservable(
    @Args("id") id: string,
    @Args("reservable") reservableInput: ReservableInput,
  ): Promise<ReservablePayload> {
    return this.reservablesService
      .updateById(id, reservableInput)
      .then((reservable) => ({
        reservable,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @Mutation()
  async deleteReservable(@Args("id") id: string): Promise<ReservablePayload> {
    return this.reservablesService
      .deleteById(id)
      .then((reservable) => ({
        reservable,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @ResolveReference()
  async resolveReference(
    reservableReference: ReservableReference,
  ): Promise<Reservable | undefined> {
    return this.reservablesService.findById(reservableReference.id)
  }
}
