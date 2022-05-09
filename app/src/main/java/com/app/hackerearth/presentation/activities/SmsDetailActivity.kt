package com.app.hackerearth.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.app.hackerearth.core.Constants.SMS_DATA
import com.app.hackerearth.core.Utility.copyToClipBoard
import com.app.hackerearth.core.Utility.getDate
import com.app.hackerearth.databinding.ActivitySmsDetailBinding
import com.app.hackerearth.domain.model.Messages

class SmsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySmsDetailBinding
    private var message: Messages? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySmsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(SMS_DATA))
            message = intent.getSerializableExtra(SMS_DATA) as Messages

        setData()
        setListeners()
    }

    private fun setData(){
        binding.apply {
            message?.let {
                title.text = it.address
                date.text = it.date.getDate()
                body.text = it.body
                if (it.otp.isEmpty()){
                    binding.otpView.visibility = View.GONE
                } else {
                    binding.otpView.visibility = View.VISIBLE
                    binding.otpView.text = it.otp
                }
            }
        }
    }

    private fun setListeners(){
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.otpView.setOnClickListener {
            message?.otp?.let { otp ->
                copyToClipBoard(otp)
            }
        }
    }
}