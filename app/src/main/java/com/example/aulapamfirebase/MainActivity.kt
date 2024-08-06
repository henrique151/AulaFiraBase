package com.example.aulapamfirebase

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aulapamfirebase.ui.theme.AulaPAMFireBaseTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AulaPAMFireBaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(db)
                }
            }
        }
    }
}

@Composable
fun App(db: FirebaseFirestore) {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "App Firebase Firestore",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Nome:",
                modifier = Modifier.weight(0.3f)
            )
            TextField(
                value = nome,
                onValueChange = { nome = it },
                modifier = Modifier.weight(0.7f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Telefone:",
                modifier = Modifier.weight(0.3f)
            )
            TextField(
                value = telefone,
                onValueChange = { telefone = it },
                modifier = Modifier.weight(0.7f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val city = hashMapOf(
                    "nome" to nome,
                    "telefone" to telefone
                )
                db.collection("AulaPAM").document("Clientes")
                    .set(city)
                    .addOnSuccessListener {
                        Log.d(ContentValues.TAG, "Documento escrito com sucesso!")
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Erro ao escrever o documento", e)
                    }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Cadastrar")
        }
    }
}
