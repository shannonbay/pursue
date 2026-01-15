package com.example.pursue

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

/**
 * 4.1.3 Create Identity Screen
 *
 * Handles display name input and (optional) profile picture placeholder.
 * On continue it generates a dummy 12‑word seed phrase and passes it
 * to the next step.
 */
class CreateIdentityFragment : Fragment() {

    interface Callbacks {
        fun onIdentityCreated(seedWords: List<String>)
    }

    private var callbacks: Callbacks? = null

    private lateinit var nameInput: EditText
    private lateinit var charCounter: TextView
    private lateinit var continueButton: Button
    private lateinit var nameError: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as? Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_create_identity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameInput = view.findViewById(R.id.input_display_name)
        charCounter = view.findViewById(R.id.text_char_counter)
        continueButton = view.findViewById(R.id.button_continue)
        nameError = view.findViewById(R.id.text_name_error)

        // Initialize character counter to show 0/30
        charCounter.text = getString(R.string.display_name_char_counter, 0)

        nameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val length = s?.length ?: 0
                charCounter.text = getString(R.string.display_name_char_counter, length)
                val valid = length in 1..30
                continueButton.isEnabled = valid
                nameError.isVisible = !valid && length > 0
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        continueButton.setOnClickListener {
            if (validate()) {
                // NOTE: For now we generate a fixed dummy seed phrase.
                val seed = listOf(
                    "example", "words", "here", "from", "the", "seed",
                    "another", "set", "of", "recovery", "phrase", "words"
                )
                callbacks?.onIdentityCreated(seed)
            }
        }
    }

    private fun validate(): Boolean {
        val length = nameInput.text?.length ?: 0
        val valid = length in 1..30
        nameError.isVisible = !valid
        return valid
    }

    companion object {
        fun newInstance(): CreateIdentityFragment = CreateIdentityFragment()
    }
}

