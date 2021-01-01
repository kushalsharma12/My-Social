package com.problemsolvers.mysocial.Dao

import android.app.DownloadManager
import androidx.core.app.TaskStackBuilder
import com.google.android.gms.tasks.Task

import com.google.firebase.firestore.FirebaseFirestore
import com.problemsolvers.mysocial.models.User
import kotlinx.coroutines.Dispatchers
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Document


class UserDao {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    fun addUser(user: User?) {
        user?.let {

            GlobalScope.launch(Dispatchers.IO) {
                usersCollection.document(user.uid).set(it)
            }

            // here the adduser fun is for adding user to the firebase
            // in userCollection.docum... we want that joh hmaari collection me entry banne usski joh
            // id ho, voh hmare user id ke equal ho.
            // it denotes the user
            // at the end we added the line usersCollection.document in GlobleScope.. coz
            // we want to do the work in the background thread

            // ab jaha aap user ko sign in karwa rahe ho vahi aap is function ko i.e. addUser fun ko
            // lga sakte ho.
        }
    }

    fun getUserById(uId: String): Task<DocumentSnapshot> {
        return usersCollection.document(uId).get()

    }


}