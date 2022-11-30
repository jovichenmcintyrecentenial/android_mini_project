package com.centennial.team15_mapd711_miniproject_phoneapp.models

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.*
import com.google.firebase.ktx.Firebase

class Database {
    companion object {
        private var db:FirebaseFirestore? = null

        fun initDatabase(context:Context) {
            FirebaseApp.initializeApp(context)
            db  = Firebase.firestore
        }

        fun getDB():FirebaseFirestore?{
            return db
        }
    }
}