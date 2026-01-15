package com.example.pursue

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * 4.1.5 Seed Phrase Verification Screen
 *
 * Asks the user to enter three specific words from the seed phrase.
 * If any are incorrect, shows an error and allows retry.
 */
class SeedVerificationFragment : Fragment() {

    interface Callbacks {
        fun onSeedVerified()
    }

    private var callbacks: Callbacks? = null

    private lateinit var inputWord3: EditText
    private lateinit var inputWord7: EditText
    private lateinit var inputWord11: EditText
    private lateinit var verifyButton: Button
    private lateinit var errorText: TextView

    private lateinit var seedWords: List<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val words = requireArguments().getStringArrayList(ARG_SEED_WORDS)
        seedWords = words?.toList() ?: emptyList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_seed_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputWord3 = view.findViewById(R.id.input_word_3)
        inputWord7 = view.findViewById(R.id.input_word_7)
        inputWord11 = view.findViewById(R.id.input_word_11)
        verifyButton = view.findViewById(R.id.button_verify_continue)
        errorText = view.findViewById(R.id.text_verification_error)

        verifyButton.setOnClickListener {
            if (verifyWords()) {
                callbacks?.onSeedVerified()
            } else {
                errorText.visibility = View.VISIBLE
            }
        }
    }

    private fun verifyWords(): Boolean {
        if (seedWords.size < 12) {
            Toast.makeText(requireContext(), R.string.error_seed_unavailable, Toast.LENGTH_SHORT)
                .show()
            return false
        }

        val w3 = inputWord3.text.toString().trim()
        val w7 = inputWord7.text.toString().trim()
        val w11 = inputWord11.text.toString().trim()

        val correct3 = seedWords[2].equals(w3, ignoreCase = true)
        val correct7 = seedWords[6].equals(w7, ignoreCase = true)
        val correct11 = seedWords[10].equals(w11, ignoreCase = true)

        return correct3 && correct7 && correct11
    }

    companion object {
        private const val ARG_SEED_WORDS = "seed_words"

        fun newInstance(seedWords: ArrayList<String>): SeedVerificationFragment {
            val fragment = SeedVerificationFragment()
            fragment.arguments = Bundle().apply {
                putStringArrayList(ARG_SEED_WORDS, seedWords)
            }
            return fragment
        }
    }
}

