import { Parent, ResolveField, Resolver } from "@nestjs/graphql"

import { Reservable } from "./reservables.model"
import { ReservablesService } from "./reservables.service"
import { Reservation } from "./reservations.model"

@Resolver("Reservation")
export class ReservationsResolver {
  constructor(private readonly reservablesService: ReservablesService) {}

  @ResolveField("reservable")
  async reservable(@Parent() reservation: Reservation): Promise<Reservable | undefined> {
    return this.reservablesService.findById(reservation.reservableId)
  }
}
