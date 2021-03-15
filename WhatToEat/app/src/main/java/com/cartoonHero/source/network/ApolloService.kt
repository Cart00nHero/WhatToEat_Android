package com.cartoonHero.source.network

import com.apollographql.apollo.ApolloClient

val apolloClient: ApolloClient = ApolloClient.builder()
    .serverUrl("http://54.95.133.239:8080/gql/api")
    .build()