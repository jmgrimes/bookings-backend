import { buildSubgraphSchema, printSubgraphSchema } from "@apollo/subgraph"
import { GraphQLTypesLoader } from "@nestjs/graphql"
import gql from "graphql-tag"

(async () => {
  const path = process.argv[2]
  const graphQLTypesLoader = new GraphQLTypesLoader()
  const mergedTypes = await graphQLTypesLoader.mergeTypesByPaths(`${path}/**/*.graphql`)
  const typeDefs = gql`
    ${mergedTypes}
  `
  const subgraphSchema = buildSubgraphSchema(typeDefs)
  return printSubgraphSchema(subgraphSchema)
})()
