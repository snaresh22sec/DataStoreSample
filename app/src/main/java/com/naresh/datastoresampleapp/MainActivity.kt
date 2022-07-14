package com.naresh.datastoresampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.naresh.datastoresampleapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager(applicationContext)

        binding.btnSave.setOnClickListener{
            storeUser()
        }

        observerData()
    }
    private fun storeUser(){

        val name = binding.etName.text.toString()
        val age = binding.etAge.text.toString().toInt()

        lifecycleScope.launch {
            userManager.storeUserData(age,name)
            Toast.makeText(this@MainActivity,
                "User data saved!!",
                Toast.LENGTH_SHORT).show()

            binding.etAge.text.clear()
            binding.etName.text.clear()
        }
    }

    private fun observerData(){

        userManager.userAgeFlow.asLiveData().observe(this){ age->

            age?.let {
                binding.tvAge.text = "Age: $age"
            }
        }

        userManager.userNameFlow.asLiveData().observe(this){ name->
            name?.let {
                binding.tvName.text = "Name: $name"
            }
        }
    }
}