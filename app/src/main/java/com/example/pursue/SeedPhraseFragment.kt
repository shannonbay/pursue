package com.example.pursue

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 4.1.4 Seed Phrase Backup Screen
 *
 * Shows the 12‑word seed phrase in a 2‑column grid, allows copying,
 * and requires the user to confirm they have written it down before
 * continuing.
 */
class SeedPhraseFragment : Fragment() {

    interface Callbacks {
        fun onSeedConfirmed(seedWords: List<String>)
    }

    private var callbacks: Callbacks? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var copyButton: Button
    private lateinit var writtenButton: Button
    private lateinit var writtenCheckbox: com.google.android.material.checkbox.MaterialCheckBox

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
        return inflater.inflate(R.layout.fragment_seed_phrase, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_seed_words)
        copyButton = view.findViewById(R.id.button_copy_seed)
        writtenButton = view.findViewById(R.id.button_written_down)
        writtenCheckbox = view.findViewById(R.id.checkbox_written_down)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = SeedAdapter(seedWords)

        copyButton.setOnClickListener {
            val clipboard =
                requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val text = seedWords.joinToString(" ")
            clipboard.setPrimaryClip(ClipData.newPlainText("Seed phrase", text))
            Toast.makeText(requireContext(), R.string.copied_seed, Toast.LENGTH_SHORT).show()
        }

        writtenCheckbox.setOnCheckedChangeListener { _, isChecked ->
            writtenButton.isEnabled = isChecked
        }

        writtenButton.setOnClickListener {
            if (writtenCheckbox.isChecked) {
                callbacks?.onSeedConfirmed(seedWords)
            }
        }
    }

    private class SeedAdapter(
        private val words: List<String>
    ) : RecyclerView.Adapter<SeedAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val index: TextView = itemView.findViewById(R.id.text_word_index)
            val word: TextView = itemView.findViewById(R.id.text_word_value)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_seed_word, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val index = position + 1
            holder.index.text = index.toString()
            holder.word.text = words[position]
        }

        override fun getItemCount(): Int = words.size
    }

    companion object {
        private const val ARG_SEED_WORDS = "seed_words"

        fun newInstance(seedWords: ArrayList<String>): SeedPhraseFragment {
            val fragment = SeedPhraseFragment()
            fragment.arguments = Bundle().apply {
                putStringArrayList(ARG_SEED_WORDS, seedWords)
            }
            return fragment
        }
    }
}

