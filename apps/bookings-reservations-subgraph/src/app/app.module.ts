import { ApolloServerPluginInlineTraceDisabled } from "@apollo/server/dist/esm/plugin/disabled"
import { ApolloServerPluginLandingPageLocalDefault } from "@apollo/server/dist/esm/plugin/landingPage/default"
import { ApolloDriverConfig, ApolloFederationDriver } from "@nestjs/apollo"
import { Module } from "@nestjs/common"
import { ConfigModule, ConfigService } from "@nestjs/config"
import { GraphQLModule } from "@nestjs/graphql"
import { MongooseModule } from "@nestjs/mongoose"

import { ReservablesModule } from "@bookings-backend/reservables"
import { ReservationsModule } from "@bookings-backend/reservations"

@Module({
  imports: [
    ConfigModule.forRoot(),
    GraphQLModule.forRoot<ApolloDriverConfig>({
      driver: ApolloFederationDriver,
      path: "/api/graphql",
      playground: false,
      plugins: [ApolloServerPluginInlineTraceDisabled(), ApolloServerPluginLandingPageLocalDefault()],
      typePaths: ["./**/*.graphql"],
    }),
    MongooseModule.forRootAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: async (configService: ConfigService) => ({
        uri: configService.get<string>("MONGODB_URI"),
      }),
    }),
    ReservablesModule,
    ReservationsModule,
  ],
})
export class AppModule {}
