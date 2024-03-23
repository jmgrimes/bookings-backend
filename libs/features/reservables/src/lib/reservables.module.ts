import { Module } from "@nestjs/common"
import { MongooseModule } from "@nestjs/mongoose"

import { ReservablesResolver } from "./reservables.resolver"
import { ReservableCollection, ReservableSchema } from "./reservables.schema"
import { ReservablesService } from "./reservables.service"
import { ReservationsResolver } from "./reservations.resolver"

@Module({
  imports: [
    MongooseModule.forFeature([
      {
        name: ReservableCollection.name,
        schema: ReservableSchema,
      },
    ]),
  ],
  providers: [
    ReservablesResolver,
    ReservablesService,
    ReservationsResolver,
  ],
})
export class ReservablesModule {}
