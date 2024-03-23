import { Day, Session } from "@bookings-backend/enums"
import { PayloadError } from "@bookings-backend/payloads"

export enum ReservableType {
  Equipment = "Equipment",
  Room = "Room",
}

export class ReservableTypes {
  private static values = [
    ReservableType.Room,
    ReservableType.Equipment
  ]
  static valueOf(key: string): ReservableType | undefined {
    return this.values.find(value => value.toString() === key)
  }
}

export interface ReservableModel {
  name: string
  description: string
  type: ReservableType
  days: Day[]
  sessions: Session[]
}

export interface Reservable extends ReservableModel {
  id: string
}

export interface ReservablePayload {
  reservable?: Reservable
  errors?: Array<PayloadError>
}
