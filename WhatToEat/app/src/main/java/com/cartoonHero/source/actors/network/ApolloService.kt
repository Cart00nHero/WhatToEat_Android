package com.cartoonHero.source.actors.network

import com.apollographql.apollo.ApolloClient

val apolloClient: ApolloClient = ApolloClient.builder()
    .serverUrl("http://54.95.133.239:8080/gql/api")
    .build()