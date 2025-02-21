package com.cardgamesstudio.monstersclash

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.cardgamesstudio.monstersclash.databinding.ActivityMainBinding
import com.cardgamesstudio.monstersclash.model.repository_data.CardEntity
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var supabaseClient: SupabaseClient
    private var isDataLoaded = false

    @OptIn(ExperimentalSerializationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supabaseClient = createSupabaseClient(
            supabaseUrl = getString(R.string.SUPABASE_URL),
            supabaseKey =  getString(R.string.SUPABASE_ANON_KEY)
        ) {
            install(Postgrest)


            defaultSerializer = KotlinXSerializer(Json {
                encodeDefaults = true
                ignoreUnknownKeys = true
                prettyPrint = true
                namingStrategy = JsonNamingStrategy.SnakeCase
            })
        }

        loadData()

        binding.playBtn.isEnabled = false

        binding.playBtn.setOnClickListener {
            Toast.makeText(this, "Botão clicado!", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            Toast.makeText(this@MainActivity, "Carregando recursos", Toast.LENGTH_SHORT).show()

            binding.progressBar.visibility = android.view.View.VISIBLE

            var cardList = listOf<CardEntity>()
            try {
                withContext(Dispatchers.IO) {
                    cardList = supabaseClient
                        .from("cards")
                        .select(Columns.ALL)

                        .decodeList<CardEntity>()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if(cardList.isNotEmpty()) {

                }
                binding.progressBar.visibility = android.view.View.GONE

                binding.playBtn.isEnabled = isDataLoaded
            }
        }
    }
}