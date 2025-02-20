package com.example.resumebuildanalyze

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        setupViews()
        setupLoadingDialog()
    }

    private fun setupViews() {
        val nameInput = findViewById<TextInputEditText>(R.id.nameInput)
        val emailInput = findViewById<TextInputEditText>(R.id.email)
        val passwordInput = findViewById<TextInputEditText>(R.id.password)
        val confirmPasswordInput = findViewById<TextInputEditText>(R.id.confirmPasswordInput)
        val signupButton = findViewById<MaterialButton>(R.id.signupl)

        nameInput.addTextChangedListener { validateInputs(nameInput, emailInput, passwordInput, confirmPasswordInput, signupButton) }
        emailInput.addTextChangedListener { validateInputs(nameInput, emailInput, passwordInput, confirmPasswordInput, signupButton) }
        passwordInput.addTextChangedListener { validateInputs(nameInput, emailInput, passwordInput, confirmPasswordInput, signupButton) }
        confirmPasswordInput.addTextChangedListener { validateInputs(nameInput, emailInput, passwordInput, confirmPasswordInput, signupButton) }

        signupButton.setOnClickListener {
            if (validateInputs(nameInput, emailInput, passwordInput, confirmPasswordInput, signupButton)) {
                handleSignUp(emailInput.text.toString(), passwordInput.text.toString())
            }
        }
    }

    private fun setupLoadingDialog() {
        loadingDialog = MaterialAlertDialogBuilder(this)
            .setView(
                ProgressBar(this).apply {
                    setPadding(70, 70, 70, 70)
                    indeterminateTintList = getColorStateList(com.google.android.material.R.color.design_default_color_primary)
                }
            )
            .setCancelable(false)
            .create()
    }

    private fun validateInputs(
        nameInput: TextInputEditText,
        emailInput: TextInputEditText,
        passwordInput: TextInputEditText,
        confirmPasswordInput: TextInputEditText,
        signupButton: MaterialButton
    ): Boolean {
        var isValid = true

        when {
            nameInput.text.isNullOrEmpty() -> {
                nameInput.error = "Name is required"
                isValid = false
            }
            nameInput.text!!.length < 3 -> {
                nameInput.error = "Name must be at least 3 characters"
                isValid = false
            }
            else -> nameInput.error = null
        }

        when {
            emailInput.text.isNullOrEmpty() -> {
                emailInput.error = "Email is required"
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailInput.text!!).matches() -> {
                emailInput.error = "Invalid email format"
                isValid = false
            }
            else -> emailInput.error = null
        }

        when {
            passwordInput.text.isNullOrEmpty() -> {
                passwordInput.error = "Password is required"
                isValid = false
            }
            passwordInput.text!!.length < 6 -> {
                passwordInput.error = "Password must be at least 6 characters"
                isValid = false
            }
            else -> passwordInput.error = null
        }

        when {
            confirmPasswordInput.text.isNullOrEmpty() -> {
                confirmPasswordInput.error = "Please confirm password"
                isValid = false
            }
            confirmPasswordInput.text.toString() != passwordInput.text.toString() -> {
                confirmPasswordInput.error = "Passwords do not match"
                isValid = false
            }
            else -> confirmPasswordInput.error = null
        }

        signupButton.isEnabled = isValid
        return isValid
    }

    private fun handleSignUp(email: String, password: String) {
        loadingDialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                loadingDialog.dismiss()
                if (task.isSuccessful) {
                    Snackbar.make(findViewById(android.R.id.content),
                        "Account created successfully",
                        Snackbar.LENGTH_SHORT)
                        .setAnchorView(findViewById(R.id.signupl))
                        .show()

                    startActivity(Intent(this, Choice::class.java))
                    finishAffinity()
                } else {
                    Snackbar.make(findViewById(android.R.id.content),
                        "Sign up failed: ${task.exception?.message}",
                        Snackbar.LENGTH_LONG)
                        .setAction("Retry") {
                            handleSignUp(email, password)
                        }
                        .show()
                }
            }
    }
}