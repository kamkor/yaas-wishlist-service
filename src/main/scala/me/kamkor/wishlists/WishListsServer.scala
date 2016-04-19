package me.kamkor.wishlists

import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import me.kamkor.wishlists.controllers.WishListsController
import me.kamkor.wishlists.modules.WishListsJacksonModule
import me.kamkor.wishlists.oauth.OAuthModule
import me.kamkor.wishlists.repository.memory.InMemoryWishListsRepositoryModule

object WishListsServerMain extends WishListsServer

/*
FIXME TODO
FIXME circuit breaker to document repository
FIXME endpoint tests
FIXME MDC logging with propagation
FIXME configurability with ENV variables
FIXME deployment
FIXME error handling (yaas error message)
FIXME YaaS Aware object (hybris-tenant and other parameters send by YaaS api proxy)
FIXME api console and raml

 */

class WishListsServer extends HttpServer {

  override val modules = Seq(oauthModule, wishListsModule)

  def oauthModule = OAuthModule

  def wishListsModule = InMemoryWishListsRepositoryModule

  override def jacksonModule = WishListsJacksonModule

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[CommonFilters]
      .add[WishListsController]
  }

}
