import { Prop, Schema, SchemaFactory } from "@nestjs/mongoose"
import { HydratedDocument } from "mongoose"

@Schema()
export class ReservableCollection {
  @Prop({ required: true }) name: string
  @Prop({ required: true }) description: string
  @Prop({ required: true }) type: string
  @Prop({ required: true }) days: Array<string>
  @Prop({ required: true }) sessions: Array<string>
}

export type ReservableDocument = HydratedDocument<ReservableCollection>
export const ReservableSchema = SchemaFactory.createForClass(ReservableCollection)
