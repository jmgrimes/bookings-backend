import { Parent, ResolveField, Resolver, ResolveReference } from "@nestjs/graphql"

import { Reservation } from "./reservations.model"
import { ReservationsService } from "./reservations.service"
import { User } from "./users.model"

export interface UserRepresentation {
  __typename: "User"
  id: string
}

@Resolver("User")
export class UsersResolver {
  constructor(private readonly reservationsService: ReservationsService) {}

  @ResolveField("reservations")
  async reservations(@Parent() user: User): Promise<Array<Reservation>> {
    return this.reservationsService.findByUserId(user.id)
  }

  @ResolveReference()
  async resolveReference(userRepresentation: UserRepresentation): Promise<User> {
    return {
      id: userRepresentation.id
    }
  }
}
