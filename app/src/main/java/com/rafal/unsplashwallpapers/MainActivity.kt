package com.rafal.unsplashwallpapers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.rafal.unsplashwallpapers.databinding.ActivityMainBinding
import com.rafal.unsplashwallpapers.model.UnsplashApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var unsplashApi: UnsplashApi

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        lifecycleScope.launch {
            val response = unsplashApi.searchPhotos("car").awaitResponse()
            Log.d("Unsplash", "Response: ${response.body()}")
            withContext(Dispatchers.Main) {
                Glide.with(applicationContext).load(response.body()!!.results[1].urls.regular).into(binding.imageView)
            }

        }
    }
}