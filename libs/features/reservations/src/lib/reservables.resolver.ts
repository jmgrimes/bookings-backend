import { Parent, ResolveField, Resolver } from "@nestjs/graphql"

import { Reservable } from "./reservables.model"
import { Reservation } from "./reservations.model"
import { ReservationsService } from "./reservations.service"

@Resolver("Reservable")
export class ReservablesResolver {
  constructor(private readonly reservationsService: ReservationsService) {}

  @ResolveField("reservations")
  async reservations(@Parent() reservable: Reservable): Promise<Array<Reservation>> {
    return this.reservationsService.findByReservableId(reservable.id)
  }
}
