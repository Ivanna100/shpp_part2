package com.example.task_3.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VBinding : ViewBinding>(private val inflaterMethod: (LayoutInflater) -> VBinding) :
    AppCompatActivity() {

    private var _binding: VBinding? = null
    val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflaterMethod.invoke(layoutInflater)
        setContentView(binding.root)
    }
}