import { Module } from "@nestjs/common"
import { MongooseModule } from "@nestjs/mongoose"

import { ReservablesResolver } from "./reservables.resolver"
import { ReservationsResolver } from "./reservations.resolver"
import { ReservationCollection, ReservationSchema } from "./reservations.schema"
import { ReservationsService } from "./reservations.service"
import { UsersResolver } from "./users.resolver"

@Module({
  imports: [
    MongooseModule.forFeature([
      {
        name: ReservationCollection.name,
        schema: ReservationSchema,
      },
    ]),
  ],
  providers: [
    ReservablesResolver,
    ReservationsResolver,
    ReservationsService,
    UsersResolver,
  ],
})
export class ReservationsModule {}
