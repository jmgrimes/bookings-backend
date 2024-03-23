import { PayloadError } from "@bookings-backend/payloads"

export interface UserModel {
  firstName: string
  lastName: string
  emailAddress: string
  title: string
  image?: string
  notes?: string
}

export interface User extends UserModel {
  id: string
}

export interface UserPayload {
  errors?: Array<PayloadError>
  user?: User
}
