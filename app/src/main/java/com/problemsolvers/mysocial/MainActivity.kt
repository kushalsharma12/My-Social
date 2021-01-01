package com.problemsolvers.mysocial

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.problemsolvers.mysocial.Dao.PostDao
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.firestore.Query
import com.problemsolvers.mysocial.models.Post

class MainActivity : AppCompatActivity(), IPostAdapter {

    private lateinit var postDao: PostDao
    private lateinit var adapter: PostAdapter

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener{
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        postDao = PostDao()
        val postsCollections = postDao.postCollections
        val query = postsCollections.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()

        adapter = PostAdapter(recyclerViewOptions, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikeClicked(postId: String) {
        postDao.updateLikes(postId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.si3)
        {
            val dialogBuilder = AlertDialog.Builder(this)


            dialogBuilder.setMessage("Are you sure?")

                .setCancelable(false)

                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id ->
                    Firebase.auth.signOut()
                    val Intent = Intent(this,Login_Activity::class.java)
                    startActivity(Intent)
                    finish()


                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })


            val alert = dialogBuilder.create()

            alert.setTitle("Sign Out")

            alert.show()

        }
        return super.onOptionsItemSelected(item)

    }


}

