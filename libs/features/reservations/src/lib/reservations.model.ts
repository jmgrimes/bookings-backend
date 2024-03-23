import { Session } from "@bookings-backend/enums"
import { PayloadError } from "@bookings-backend/payloads"

export interface ReservationModel {
  title: string
  description: string
  date: Date
  reservableId: string
  session: Session
  userId: string
}

export interface Reservation extends ReservationModel {
  id: string
}

export interface ReservationPayload {
  errors?: Array<PayloadError>
  reservation?: Reservation
}
