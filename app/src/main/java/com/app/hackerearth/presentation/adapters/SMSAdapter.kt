package com.app.hackerearth.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.hackerearth.core.Utility.getDate
import com.app.hackerearth.databinding.SmsListItemBinding
import com.app.hackerearth.domain.model.Messages

class SMSAdapter: RecyclerView.Adapter<SMSAdapter.SMSHolder>() {

    inner class SMSHolder(val binding: SmsListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSHolder {
        return SMSHolder(
            SmsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SMSHolder, position: Int) {
        val item = diffUtil.currentList[position]
        with(holder) {
            with(item) {
                binding.title.text = address
                binding.date.text = date.getDate()
                binding.body.text = body
                if (otp.isEmpty()){
                    binding.otpView.visibility = View.GONE
                } else {
                    binding.otpView.visibility = View.VISIBLE
                    binding.otpView.text = otp
                    binding.otpView.setOnClickListener {
                        onCopyClickClick?.let { it(otp) }
                    }
                }
            }
            binding.root.setOnClickListener {
                onSmsClickClick?.let { it(item) }
            }
        }
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Messages>(){
        override fun areItemsTheSame(oldItem: Messages, newItem: Messages): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Messages, newItem: Messages): Boolean{
            return oldItem == newItem
        }
    }

    val diffUtil =  AsyncListDiffer(this, diffCallback)

    private var onSmsClickClick: ((Messages) -> Unit)? = null

    fun onSmsClickListener(listener: (Messages) -> Unit)  {
        onSmsClickClick = listener
    }

    private var onCopyClickClick: ((String) -> Unit)? = null

    fun onCopyClickListener(listener: (String) -> Unit)  {
        onCopyClickClick = listener
    }
}