package com.cartoonHero.source.redux.actions

import CreateGourmetMutation
import DislikeGourmetMutation
import SignFoodieMutation
import UpdateGourmetMutation
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.cartoonHero.source.enities.GQInputObject
import com.cartoonHero.source.actors.network.apolloClient
import com.cartoonHero.source.redux.appStore
import org.rekotlin.Action
import type.SignData

enum class NetWorkStatus {
    NONE, STARTED, SUCCESS, FAILED
}

class SignFoodieAction: Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData: SignFoodieMutation.SignFoodie? = null
}
fun signFoodieAction(signData: SignData):SignFoodieAction {
    val action = SignFoodieAction()
    val mutation = SignFoodieMutation(signData = signData)
    apolloClient.mutate(mutation).enqueue(
        object : ApolloCall.Callback<SignFoodieMutation.Data>() {
        override fun onResponse(response: Response<SignFoodieMutation.Data>) {
            action.responseData = response.data?.signFoodie
            action.status = NetWorkStatus.SUCCESS
            appStore.dispatch(action)
        }
        override fun onFailure(e: ApolloException) {
            action.status = NetWorkStatus.FAILED
            appStore.dispatch(action)
        }
    })
    return action
}

class CreateGourmetAction: Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData: CreateGourmetMutation.CreateGourmet? = null
}
fun createGourmetAction(foodieId: String,inputObj: GQInputObject): CreateGourmetAction {
    val action = CreateGourmetAction()
    val mutation = CreateGourmetMutation(
        foodieId = foodieId,
        address = inputObj.address,
        shopBranch = inputObj.shopBranch,
        shop = inputObj.shop
    )
    apolloClient.mutate(mutation).enqueue(
        object : ApolloCall.Callback<CreateGourmetMutation.Data>() {
        override fun onResponse(response: Response<CreateGourmetMutation.Data>) {
            action.responseData = response.data?.createGourmet
            action.status = NetWorkStatus.SUCCESS
            appStore.dispatch(action)
        }

        override fun onFailure(e: ApolloException) {
            action.status = NetWorkStatus.FAILED
            appStore.dispatch(action)
        }
    })
    return action
}

class UpdateGourmetAction: Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData:
            UpdateGourmetMutation.UpdateGourmet? = null
}
fun updateGourmetAction(foodieId: String,inputObj: GQInputObject): UpdateGourmetAction {
    val action = UpdateGourmetAction()
    val mutation = UpdateGourmetMutation(
        foodieId = foodieId,
        branchId = inputObj.branchId,
        address = inputObj.address.toInput(),
        branch = inputObj.shopBranch,
        shop = inputObj.shop.toInput()
    )
    apolloClient.mutate(mutation).enqueue(
        object : ApolloCall.Callback<UpdateGourmetMutation.Data>(){
        override fun onResponse(response: Response<UpdateGourmetMutation.Data>) {
            action.responseData = response.data?.updateGourmet
            action.status = NetWorkStatus.SUCCESS
            appStore.dispatch(action)
        }

        override fun onFailure(e: ApolloException) {
            action.status = NetWorkStatus.FAILED
            appStore.dispatch(action)
        }

    })
    return action
}

class DislikeGourmetAction:Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData:
            DislikeGourmetMutation.DislikeGourmet? = null
}
fun dislikeGourmetAction(foodieId: String, branchId: String): DislikeGourmetAction {
    val action = DislikeGourmetAction()
    val mutation = DislikeGourmetMutation(
        foodieId = foodieId,
        branchId = branchId
    )
    apolloClient.mutate(mutation).enqueue(
        object : ApolloCall.Callback<DislikeGourmetMutation.Data>(){
        override fun onResponse(response: Response<DislikeGourmetMutation.Data>) {
            action.responseData = response.data?.dislikeGourmet
            action.status = NetWorkStatus.SUCCESS
            appStore.dispatch(action)
        }

        override fun onFailure(e: ApolloException) {
            action.status = NetWorkStatus.FAILED
            appStore.dispatch(action)
        }

    })
    return action
}