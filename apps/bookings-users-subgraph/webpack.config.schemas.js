module.exports = [
  {
    glob: "*.graphql",
    input: "libs/features/users/src/assets/",
    output: "./users/users/",
  },
  {
    glob: "*.graphql",
    input: "libs/shared/payloads/src/assets/",
    output: "./users/payloads/",
  },
]
