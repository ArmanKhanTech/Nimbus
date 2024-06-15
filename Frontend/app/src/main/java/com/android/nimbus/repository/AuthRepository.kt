package com.android.nimbus.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

data class Result(
    val result: AuthResult?,
    val error: String
)

class AuthRepository(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun signupWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        phone: String
    ): Flow<Result> {
        return flow {
            val user = firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            saveUserDetails(user.user!!, name, phone)
            emit(Result(user, ""))
        }.catch { e ->
            emit(Result(null, e.message ?: "An error occurred"))
        }
    }

    suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Result> {
        return flow {
            val user = firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            emit(Result(user, ""))
        }.catch {
            emit(Result(null, it.message ?: "An error occurred"))
        }
    }

    suspend fun sendResetPasswordEmail(email: String): Flow<Result> {
        return flow {
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Result(null, "none"))
        }.catch {
            emit(Result(null, it.message ?: "An error occurred"))
        }
    }

    private fun saveUserDetails(
        user: FirebaseUser,
        name: String,
        phone: String
    ) {
        val userDoc = firestore.collection("users").document(user.uid)
        userDoc.set(
            hashMapOf(
                "uid" to user.uid,
                "email" to user.email,
                "name" to name,
                "phone" to phone
            )
        )
    }
}