package com.cartoonHero.source.redux.actions

import LocationsDynamicQuery
import SearchForRangeQuery
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.cartoonHero.source.network.apolloClient
import com.cartoonHero.source.redux.appStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.rekotlin.Action
import type.AddressDqCmd
import type.InputCoordinate

class LocationsDynamicQueryAction : Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData:
            List<LocationsDynamicQuery.LocationsDynamicQuery?>? = null
}

fun locationsDynamicQueryAction(
    foodieId: String?,whereCMD: AddressDqCmd): LocationsDynamicQueryAction {
    val action = LocationsDynamicQueryAction()
    CoroutineScope(Dispatchers.IO).launch {
        val query: LocationsDynamicQuery = if (foodieId != null) {
            LocationsDynamicQuery(
                foodieId = Input.optional(foodieId),whereAnd = whereCMD)
        } else {
            LocationsDynamicQuery(whereAnd = whereCMD)
        }
        val response =
            apolloClient.query(query).await()
        withContext(Dispatchers.Main) {
            when {
                response.hasErrors() -> {
                    action.status = NetWorkStatus.FAILED
                    appStore.dispatch(action)
                }
                else -> {
                    action.responseData = response.data?.locationsDynamicQuery
                    action.status = NetWorkStatus.SUCCESS
                    appStore.dispatch(action)
                }
            }
        }
    }
    return action
}
class SearchForRangeAction: Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData:
            List<SearchForRangeQuery.SearchForRange?>? = null
}
fun searchForRangeAction(
    foodieId: Input<String>, min: InputCoordinate, max: InputCoordinate): SearchForRangeAction {
    val action = SearchForRangeAction()
    CoroutineScope(Dispatchers.IO).launch {
        val response =
            apolloClient.query(SearchForRangeQuery(
                foodieId = foodieId,
                minCoordinate = min,
                maxCoordinate = max
            )).await()
        withContext(Dispatchers.Main){
            when {
                response.hasErrors() -> {
                    action.status = NetWorkStatus.FAILED
                    appStore.dispatch(action)
                }
                else -> {
                    action.responseData = response.data?.searchForRange
                    action.status = NetWorkStatus.SUCCESS
                    appStore.dispatch(action)
                }
            }
        }
    }
    return action
}

class ApolloClearCacheAction: Action {
    init {
        clearCacheAction()
    }
    private fun clearCacheAction() {
        apolloClient.clearNormalizedCache()
    }
}