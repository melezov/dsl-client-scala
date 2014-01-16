package com.dslplatform.api.client

import com.dslplatform.api.patterns._
import scala.reflect.ClassTag
import scala.concurrent.Future

class HttpCrudProxy(httpClient: HttpClient) extends CrudProxy {
  import HttpClientUtil._

  private val CRUD_URI = "Crud.svc"

  def read[TIdentifiable <: Identifiable: ClassTag](
    uri: String): Future[TIdentifiable] = {
    val domainName: String = httpClient.getDslName[TIdentifiable]
    httpClient.sendRequest[TIdentifiable](
      GET, CRUD_URI / domainName + "?uri=" + encode(uri), Set(200))
  }

  def create[TAggregateRoot <: AggregateRoot: ClassTag](
    aggregate: TAggregateRoot): Future[TAggregateRoot] = {
    val service: String = CRUD_URI / httpClient.getDslName[TAggregateRoot]
    httpClient.sendRequest[TAggregateRoot](
      POST(aggregate), service, Set(201))
  }

  def update[TAggregate <: AggregateRoot: ClassTag](
    aggregate: TAggregate): Future[TAggregate] = {
    val uri: String = aggregate.URI
    val domainName: String = httpClient.getDslName[TAggregate]
    httpClient.sendRequest[TAggregate](
      PUT(aggregate),
      CRUD_URI / domainName + "?uri=" + encode(uri),
      Set(200));
  }

  def delete[TAggregateRoot <: AggregateRoot: ClassTag](
    uri: String): Future[TAggregateRoot] = {
    val domainName: String = httpClient.getDslName[TAggregateRoot]
    httpClient.sendRequest[TAggregateRoot](
      DELETE,
      CRUD_URI / domainName + "?uri=" + encode(uri),
      Set(200))
  }
}