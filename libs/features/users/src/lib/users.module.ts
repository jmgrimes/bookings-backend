import { Module } from "@nestjs/common"
import { MongooseModule } from "@nestjs/mongoose"

import { UsersResolver } from "./users.resolver"
import { UserCollection, UserSchema } from "./users.schema"
import { UsersService } from "./users.service"

@Module({
  imports: [
    MongooseModule.forFeature([{ name: UserCollection.name, schema: UserSchema }]),
  ],
  providers: [
    UsersResolver,
    UsersService,
  ],
})
export class UsersModule {}
