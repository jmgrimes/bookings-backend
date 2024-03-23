import { Injectable } from "@nestjs/common"
import { InjectModel } from "@nestjs/mongoose"
import { MongoDataSource } from "apollo-datasource-mongodb"
import { Model } from "mongoose"

import { User, UserModel } from "./users.model"
import { UserCollection, UserDocument } from "./users.schema"

@Injectable()
export class UsersService extends MongoDataSource<UserDocument> {
  constructor(@InjectModel(UserCollection.name) userModel: Model<UserDocument>) {
    super({
      modelOrCollection: userModel,
    })
  }

  async findById(id: string): Promise<User | undefined> {
    return this.findOneById(id)
      .then(document => document ? this.toUser(document) : undefined)
  }

  async findAll(ids?: string[]): Promise<User[]> {
    const userDocuments = await this.findManyByIds(
      ids ? ids : await this.findAllIds(),
    )
    return userDocuments
      .filter((e): e is Exclude<typeof e, null> => e !== null)
      .filter((e): e is Exclude<typeof e, undefined> => e !== undefined)
      .map(this.toUser)
  }

  async create(userModel: UserModel): Promise<User> {
    return this.model.create(userModel).then(this.toUser)
  }

  async updateById(
    id: string,
    userModel: UserModel,
  ): Promise<User | undefined> {
    await this.deleteFromCacheById(id)
    await this.model.findByIdAndUpdate(id, userModel).exec()
    return this.findOneById(id)
      .then(document => document ? this.toUser(document) : undefined)
  }

  async deleteById(id: string): Promise<User | undefined> {
    await this.deleteFromCacheById(id)
    return this.model
      .findByIdAndDelete(id)
      .exec()
      .then((result) => {
        return result ? this.toUser(result) : undefined
      })
  }

  private async findAllIds(): Promise<string[]> {
    const users = await this.model.find({}, ["_id"]).exec()
    return users.map((user) => user._id.toHexString())
  }

  private toUser(userDocument: UserDocument): User {
    return {
      id: userDocument._id.toHexString(),
      firstName: userDocument.firstName,
      lastName: userDocument.lastName,
      emailAddress: userDocument.emailAddress,
      title: userDocument.title,
      image: userDocument.image,
      notes: userDocument.notes,
    }
  }
}
