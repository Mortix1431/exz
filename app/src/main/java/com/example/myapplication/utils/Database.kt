package com.example.myapplication.utils

import android.util.Log
import com.example.myapplication.data.Product
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage

object Database {

    private val client = createSupabaseClient(
        supabaseUrl = "https://kfxxpnkcsrnutrqfedbm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtmeHhwbmtjc3JudXRycWZlZGJtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDM2MTE1OTcsImV4cCI6MjAxOTE4NzU5N30.LOvztTDa6OIjIV2EV20Jb5CheRbcMFzn13Q8ACsmdJc"
    ){
        install(GoTrue)
        install(Postgrest)
        install(Storage)
    }

    private var selected_item = 0

    fun getDatabase() : SupabaseClient{
        return client
    }

    suspend fun Auth(mail : String, pass : String) : Boolean{
        try {
            client.gotrue.loginWith(Email) {
                email = mail
                password = pass
            }
            return true
        }catch (ex : Exception){
            return false
        }
    }

    fun getUser() : UserInfo? {
        try {
            return client.gotrue.currentUserOrNull()
        }catch (ex: Exception){
            return null
        }
    }

    fun setSelectedProduct(id : Int){
        selected_item = id
    }
    suspend fun getSelectedProduct() : Product{
        return client.postgrest["product"].select() { Product::id eq selected_item }.decodeSingle<Product>()
    }

    suspend fun Register(mail : String, pass : String) : Boolean{
        try{
            client.gotrue.signUpWith(Email){
                email = mail
                password = pass
            }
            return true
        }catch (ex: Exception){
            Log.e(";sa;d",ex.message.toString())
            return false
        }
    }



}