package com.cartoonHero.source.redux.actions

import CreateGourmetMutation
import DislikeGourmetMutation
import SignFoodieMutation
import SignFoodieWithAppleMutation
import UpdateGourmetMutation
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.cartoonHero.source.props.enities.GQInputObject
import com.cartoonHero.source.network.apolloClient
import com.cartoonHero.source.redux.appStore
import org.rekotlin.Action
import type.SignData

enum class NetWorkStatus {
    NONE, STARTED, SUCCESS, FAILED
}
open class MutationAction<T>: Action {
    var status: NetWorkStatus = NetWorkStatus.STARTED
    var responseData: T? = null
}
class SignWithAppleAction:
    MutationAction<SignFoodieWithAppleMutation.SignFoodieWithApple>()
fun signWithAppleAction(signData: SignData): SignWithAppleAction {
    val action = SignWithAppleAction()
    val mutation = SignFoodieWithAppleMutation(signData)
    apolloClient.mutate(mutation).enqueue(
        object : ApolloCall.Callback<SignFoodieWithAppleMutation.Data>() {
            override fun onResponse(
                response: Response<SignFoodieWithAppleMutation.Data>) {
                action.responseData = response.data?.signFoodieWithApple
                action.status = NetWorkStatus.SUCCESS
                appStore.dispatch(action)
            }

            override fun onFailure(e: ApolloException) {
                action.status = NetWorkStatus.FAILED
                appStore.dispatch(action)
            }

        }
    )
    return action
}
class SignFoodieAction:
    MutationAction<SignFoodieMutation.SignFoodie>()
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

class CreateGourmetAction:
    MutationAction<CreateGourmetMutation.CreateGourmet>()

fun createGourmetAction(inputObj: GQInputObject): CreateGourmetAction {
    val action = CreateGourmetAction()
    val mutation = CreateGourmetMutation(
        address = inputObj.address,
        shopBranch = inputObj.shopBranch
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

class UpdateGourmetAction:
    MutationAction<UpdateGourmetMutation.UpdateGourmet>()

fun updateGourmetAction(inputObj: GQInputObject): UpdateGourmetAction {
    val action = UpdateGourmetAction()
    val mutation = UpdateGourmetMutation(
        branchId = inputObj.branchId,
        address = inputObj.address.toInput(),
        branch = inputObj.shopBranch
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

class DislikeGourmetAction:
    MutationAction<DislikeGourmetMutation.DislikeGourmet>()
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