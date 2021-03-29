package com.cartoonHero.source.network

import com.apollographql.apollo.ApolloClient

val apolloClient: ApolloClient = ApolloClient.builder()
    .serverUrl("https://what2eat-dev.herokuapp.com/graphql/api")
    .build()