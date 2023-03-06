package com.example.hm3m6

import android.os.Build.VERSION_CODES.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.hm3m6.Adapter.HintAdapter
import com.example.hm3m6.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter :HintAdapter
    private val savedTags = hashSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClicker()
    }

    private fun initClicker() {
        initAdapter()
        saveTag()
        showHint()
    }

    private fun initAdapter() {
           adapter =HintAdapter(this::ItemClick)

        binding.rvHints.adapter = adapter
    }

    private fun ItemClick(hint: String)  {


    }

    private fun saveTag() {           // 1) this fun finds
        binding.ivSend.setOnClickListener {
            savedTags.addAll(findTag(binding.etText.text.toString()))
            binding.etText.text.clear()
        }
    }

    private fun showHint() {
        binding.etText.addTextChangedListener {
            adapter.addData(findInSaved(binding.etText.text.toString()))
        }
    }

    private fun findTag(text: String): ArrayList<String> {
        val result = arrayListOf<String>()
        val message = text.split(" ")
        for (i in message) {
            if (i.startsWith("#")) {
                result.add(i)
            }
        }
        return result
    }

    private fun findInSaved(text: String): ArrayList<String> {
        val result = arrayListOf<String>()
        val onlineTag = findTag(text)
        for (tag in onlineTag) {
            for (savedTag in savedTags) {
                if (savedTag.contains(tag) && savedTag != tag)
                    result.add(savedTag)
            }
        }
        return result
    }

    private fun setHint(newTag: String) {
        val message = binding.etText.text.toString()
        val splitedMessage = message.split(" ").toMutableList()
        val tagIndex = findIndex(splitedMessage, message)
        splitedMessage[tagIndex] = newTag
        binding.etText.setText(splitedMessage.joinToString(" "))
        binding.etText.setSelection(binding.etText.text.length)
    }

    private fun findIndex(splitedMessage: MutableList<String>, message: String): Int {
        for (i in findTag(message)) {
            if (!savedTags.contains(i))
                return splitedMessage.indexOf(i)
        }
        return 0
    }
}