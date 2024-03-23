export enum Session {
  Breakfast = "Breakfast",
  Morning = "Morning",
  Lunch = "Lunch",
  Afternoon = "Afternoon",
  Evening = "Evening",
}

export class Sessions {
  private static values = [
    Session.Breakfast,
    Session.Morning,
    Session.Lunch,
    Session.Afternoon,
    Session.Evening,
  ]
  static valueOf(key: string): Session | undefined {
    return this.values.find(value => value.toString() === key)
  }
}
