const { NxWebpackPlugin } = require("@nx/webpack")
const { join } = require("path")

const reservationsSchemaAssets = require("../bookings-reservations-subgraph/webpack.config.schemas")
const usersSchemaAssets = require("../bookings-users-subgraph/webpack.config.schemas")

module.exports = {
  output: {
    path: join(__dirname, "../../dist/apps/bookings-backend"),
  },
  plugins: [
    new NxWebpackPlugin({
      target: "node",
      compiler: "tsc",
      main: "./src/main.ts",
      tsConfig: "./tsconfig.app.json",
      assets: [
        {
          glob: "*.yaml",
          input: "./src/assets",
          output: ".",
        },
        ...reservationsSchemaAssets,
        ...usersSchemaAssets,
      ],
      optimization: false,
      outputHashing: "none",
      generatePackageJson: true,
    }),
  ],
}
