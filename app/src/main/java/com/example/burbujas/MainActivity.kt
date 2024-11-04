package com.example.burbujas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user: EditText = findViewById(R.id.txtUsername)
        val pass: EditText = findViewById(R.id.txtPassword)
        val btn: Button = findViewById(R.id.btnLogin)

        btn.setOnClickListener {
            val urlBase = "http://192.168.0.5/android_api/registro.php" // Asegúrate de que esta URL es correcta
            val queue: RequestQueue = Volley.newRequestQueue(this)
            val username = user.text.toString()
            val password = pass.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Usar una Map<Any?, Any?> para la compatibilidad con JSONObject
                val params: MutableMap<String, Any?> = HashMap()
                params["username"] = username
                params["password"] = password

                // Crear un JSONObject con el Map
                val jsonObject = JSONObject(params as Map<*, *>)

                // Configura la solicitud POST
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.POST,
                    urlBase,
                    jsonObject,
                    Response.Listener { response ->
                        val success = response.optBoolean("success", false)
                        if (success) {
                            val intent = Intent(this, Dashboard::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                )

                // Agrega la solicitud a la cola
                queue.add(jsonObjectRequest)
            } else {
                Toast.makeText(this, "Casillas vacías, reinténtelo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}



