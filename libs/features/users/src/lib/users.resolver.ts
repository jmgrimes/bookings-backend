import {
  Args,
  Mutation,
  Query,
  Resolver,
  ResolveReference,
} from "@nestjs/graphql"
import { IsEmail, IsUrl, IsNotEmpty } from "class-validator"

import { User, UserModel, UserPayload } from "./users.model"
import { UsersService } from "./users.service"

export class UserInput implements UserModel {
  @IsNotEmpty() firstName: string
  @IsNotEmpty() lastName: string
  @IsEmail() emailAddress: string
  @IsNotEmpty() title: string
  @IsUrl() image?: string
  notes?: string
}

export interface UserRepresentation {
  __typename: "User"
  id: string
}

@Resolver("User")
export class UsersResolver {
  constructor(private readonly usersService: UsersService) {}

  @Query()
  async user(@Args("id") id: string): Promise<User | undefined> {
    return this.usersService.findById(id)
  }

  @Query()
  async users(@Args("ids") ids?: string[]): Promise<User[]> {
    return this.usersService.findAll(ids)
  }

  @Mutation()
  async addUser(@Args("user") userInput: UserInput): Promise<UserPayload> {
    return this.usersService
      .create(userInput)
      .then((user) => ({
        user,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @Mutation()
  async updateUser(
    @Args("id") id: string,
    @Args("user") userInput: UserInput,
  ): Promise<UserPayload> {
    return this.usersService
      .updateById(id, userInput)
      .then((user) => ({
        user,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @Mutation()
  async deleteUser(@Args("id") id: string): Promise<UserPayload> {
    return this.usersService
      .deleteById(id)
      .then((user) => ({
        user,
      }))
      .catch((error) => ({
        errors: [{ message: error }],
      }))
  }

  @ResolveReference()
  async resolveReference(userRepresentation: UserRepresentation): Promise<User | undefined> {
    return this.usersService.findById(userRepresentation.id)
  }
}
