import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose"
import { HydratedDocument } from "mongoose"

@Schema()
export class UserCollection {
  @Prop({ required: true }) firstName: string
  @Prop({ required: true }) lastName: string
  @Prop({ required: true }) emailAddress: string
  @Prop({ required: true }) title: string
  @Prop({ required: false }) image?: string
  @Prop({ required: false }) notes?: string
}

export type UserDocument = HydratedDocument<UserCollection>
export const UserSchema = SchemaFactory.createForClass(UserCollection)
