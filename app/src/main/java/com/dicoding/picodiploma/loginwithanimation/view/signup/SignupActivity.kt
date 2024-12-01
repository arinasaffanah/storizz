package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.api.RegisterRequest
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Panggil API untuk pendaftaran
            registerUser(name, email, password)
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        val apiService = ApiConfig.getApiService()
        val registerRequest = RegisterRequest(name, email, password)

        apiService.register(registerRequest).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    // Pendaftaran berhasil
                    Toast.makeText(this@SignupActivity, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke login
                } else {
                    // Jika respons gagal, periksa kode status dan body error
                    when (response.code()) {
                        400 -> {
                            // Misalnya: jika error terkait data input seperti password yang tidak valid
                            Toast.makeText(this@SignupActivity, "Pendaftaran gagal: Cek kembali input email Anda", Toast.LENGTH_SHORT).show()
                        }
                        409 -> {
                            // Jika ada konflik, misalnya email sudah terdaftar
                            Toast.makeText(this@SignupActivity, "Pendaftaran gagal: Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                        }
                        500 -> {
                            // Jika ada kesalahan server
                            Toast.makeText(this@SignupActivity, "Pendaftaran gagal: Kesalahan server, coba lagi nanti", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Jika status error lain, tampilkan pesan default
                            val errorMessage = response.errorBody()?.string()
                            Toast.makeText(this@SignupActivity, "Pendaftaran gagal: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                // Menangani error jika terjadi kesalahan koneksi atau masalah lainnya
                Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun playAnimation() {
        // Animasi tetap sama
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title, nameTextView, nameEditTextLayout, emailTextView, emailEditTextLayout,
                passwordTextView, passwordEditTextLayout, signup
            )
            startDelay = 100
        }.start()
    }
}
