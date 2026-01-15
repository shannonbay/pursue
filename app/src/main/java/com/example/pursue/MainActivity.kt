package com.example.pursue

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Entry point for the app.
 *
 * For now this activity is responsible for implementing the first‑time user
 * experience described in section 4.1 of the UI spec.
 *
 * Behaviour:
 * - If no local identity exists → launch onboarding flow.
 * - If identity exists → TODO: launch main app (Groups / Home).
 */
class MainActivity : AppCompatActivity() {

    private val prefs: SharedPreferences by lazy {
        getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isFinishing) return

        if (hasIdentity()) {
            // TODO: When Home / Groups is implemented, navigate there instead.
            startActivity(Intent(this, OnboardingActivity::class.java))
        } else {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
        finish()
    }

    private fun hasIdentity(): Boolean {
        // Placeholder check: in a real implementation this would look for a
        // persisted identity / keypair. For UI work we keep it simple.
        return prefs.getBoolean(KEY_HAS_IDENTITY, false)
    }
    companion object {
        const val PREFS_NAME = "pursue_prefs"
        const val KEY_HAS_IDENTITY = "has_identity"
    }
}

