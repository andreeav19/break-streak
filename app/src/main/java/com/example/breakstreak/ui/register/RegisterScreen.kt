package com.example.breakstreak.ui.register

import com.example.breakstreak.viewmodel.UserViewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.breakstreak.R
import com.example.breakstreak.data.model.LocalUser

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    userViewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            stringResource(id = R.string.register_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(stringResource(id = R.string.first_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(stringResource(id = R.string.last_name)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.email)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.password)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                if (firstName.isBlank() || lastName.isBlank() ||
                    email.isBlank() || password.isBlank()
                ) {
                    errorMessage = context.getString(R.string.error_required_fields)
                    isLoading = false
                    return@Button
                }

                errorMessage = null
                isLoading = true

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                            val userData = hashMapOf(
                                "firstName" to firstName,
                                "lastName" to lastName,
                                "email" to email
                            )

                            db.collection("users").document(userId)
                                .set(userData)
                                .addOnSuccessListener {
                                    val localUser = LocalUser(
                                        userId = userId,
                                        firstName = firstName,
                                        lastName = lastName,
                                        email = email
                                    )

                                    userViewModel.insertUser(localUser)

                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.register_success),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onRegisterSuccess()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.register_firestore_error) + " ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    isLoading = false
                                }
                        } else {
                            Toast.makeText(
                                context,
                                context.getString(R.string.register_firebase_error) + " ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            isLoading = false
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.register_title))
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.go_to_login_message),
            color = Color.Blue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .clickable { onNavigateToLogin() }
        )
    }
}
