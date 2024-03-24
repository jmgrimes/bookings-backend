module.exports = [
  {
    glob: "*.graphql",
    input: "libs/features/reservables/src/assets/",
    output: "./reservations/reservables/",
  },
  {
    glob: "*.graphql",
    input: "libs/features/reservations/src/assets/",
    output: "./reservations/reservations/",
  },
  {
    glob: "*.graphql",
    input: "libs/shared/enums/src/assets/",
    output: "./reservations/enums/",
  },
  {
    glob: "*.graphql",
    input: "libs/shared/payloads/src/assets/",
    output: "./reservations/payloads/",
  },
]
