const { NxWebpackPlugin } = require("@nx/webpack")
const { join } = require("path")
const schemaAssets = require("./webpack.config.schemas")

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
      assets: [...schemaAssets],
      optimization: false,
      outputHashing: "none",
      generatePackageJson: true,
    }),
  ],
}
