package com.krayapp.convertpj2.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krayapp.convertpj2.App
import com.krayapp.convertpj2.databinding.MainFragmentBinding
import com.krayapp.convertpj2.present.FragPresenter
import com.krayapp.convertpj2.present.SchedulersList
import moxy.MvpAppCompatActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.InputStream

class MainFragment : MvpAppCompatFragment(), MainFragView {
    companion object {
        const val REQUEST_CODE = 111
        fun newInstance() = MainFragment()
    }

    private val presenter by moxyPresenter { FragPresenter(SchedulersList()) }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var inStream : InputStream? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkPermission(this.activity)
        initButton()
    }

    private fun initButton() {
        binding.chooseFile.setOnClickListener {
            openFile()
        }
        binding.convertFile.visibility = View.INVISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == MvpAppCompatActivity.RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                val uri = data.data
                inStream = context?.contentResolver?.openInputStream(uri!!)
                binding.convertFile.setOnClickListener {
                    presenter.convertFile(inStream!!)
                }
                binding.convertFile.visibility = View.VISIBLE
            }
        }
    }

    private fun openFile() {
        val intent = Intent()
            .setType("image/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun setTextView(text: String) {
        binding.result.text = text
    }
}