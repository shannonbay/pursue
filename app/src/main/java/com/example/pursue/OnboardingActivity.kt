package com.example.pursue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

/**
 * Hosts the first‑time user onboarding flow (UI spec section 4.1).
 *
 * This activity swaps simple fragments for each screen in the flow.
 * It deliberately avoids complex navigation libraries to keep the
 * initial implementation lightweight.
 */
class OnboardingActivity : AppCompatActivity(),
    WelcomeFragment.Callbacks,
    CreateIdentityFragment.Callbacks,
    SeedPhraseFragment.Callbacks,
    SeedVerificationFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.onboarding_container, WelcomeFragment.newInstance())
            }
        }
    }

    // region WelcomeFragment.Callbacks

    override fun onGetStarted() {
        // For now we skip the optional carousel and go directly to
        // identity creation as per 4.1.3.
        supportFragmentManager.commit {
            replace(R.id.onboarding_container, CreateIdentityFragment.newInstance())
            addToBackStack(null)
        }
    }

    override fun onRestoreAccount() {
        // TODO: Implement restore flow in a future iteration.
    }

    // endregion

    // region CreateIdentityFragment.Callbacks

    override fun onIdentityCreated(seedWords: List<String>) {
        supportFragmentManager.commit {
            replace(
                R.id.onboarding_container,
                SeedPhraseFragment.newInstance(ArrayList(seedWords))
            )
            addToBackStack(null)
        }
    }

    // endregion

    // region SeedPhraseFragment.Callbacks

    override fun onSeedConfirmed(seedWords: List<String>) {
        supportFragmentManager.commit {
            replace(
                R.id.onboarding_container,
                SeedVerificationFragment.newInstance(ArrayList(seedWords))
            )
            addToBackStack(null)
        }
    }

    // endregion

    // region SeedVerificationFragment.Callbacks

    override fun onSeedVerified() {
        // In a later section we will branch into backup flows.
        // For now we mark that an identity exists and finish.
        getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE)
            .edit()
            .putBoolean(MainActivity.KEY_HAS_IDENTITY, true)
            .apply()

        finish()
    }

    // endregion
}

