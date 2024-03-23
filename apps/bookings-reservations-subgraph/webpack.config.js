const { NxWebpackPlugin } = require("@nx/webpack")
const { join } = require("path")

module.exports = {
  output: {
    path: join(__dirname, "../../dist/apps/bookings-reservations-subgraph"),
  },
  plugins: [
    new NxWebpackPlugin({
      target: "node",
      compiler: "tsc",
      main: "./src/main.ts",
      tsConfig: "./tsconfig.app.json",
      assets: [
        {
          "glob": "*.graphql",
          "input": "libs/features/reservables/src/assets/",
          "output": "./schemas/reservables/"
        },
        {
          "glob": "*.graphql",
          "input": "libs/features/reservations/src/assets/",
          "output": "./schemas/reservations/"
        },
        {
          "glob": "*.graphql",
          "input": "libs/shared/enums/src/assets/",
          "output": "./schemas/enums/"
        },
        {
          "glob": "*.graphql",
          "input": "libs/shared/payloads/src/assets/",
          "output": "./schemas/payloads/"
        },
      ],
      optimization: false,
      outputHashing: "none",
      generatePackageJson: true,
    }),
  ],
}
