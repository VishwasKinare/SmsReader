package com.app.hackerearth.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.hackerearth.R
import com.app.hackerearth.core.Constants.SMS_DATA
import com.app.hackerearth.core.PermissionManager
import com.app.hackerearth.core.Utility.copyToClipBoard
import com.app.hackerearth.core.Utility.getPermissionDialog
import com.app.hackerearth.databinding.ActivityHomeBinding
import com.app.hackerearth.domain.model.Messages
import com.app.hackerearth.presentation.adapters.SMSAdapter
import com.app.hackerearth.presentation.viewmodels.HomeViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var smsAdapter: SMSAdapter

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycleView()
        setObservers()
        setListeners()
        smsListener()
        permissionManager.readSmsPermission()
    }

    private fun setListeners(){
        smsAdapter.onCopyClickListener {
            copyToClipBoard(it)
        }

        smsAdapter.onSmsClickListener{
            startActivity(Intent(this, SmsDetailActivity::class.java).apply {
                putExtra(SMS_DATA, it)
            })
        }
    }

    private fun setObservers(){
        permissionManager.smsPermission.observe(this){
            if (it){
                viewModel.getSmsList()
            } else {
                getPermissionDialog(getString(R.string.enable_sms_message))
                    .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
        }

        viewModel.smsList.observe(this){
            when {
                it.isLoading -> {
                    binding.smsRV.visibility = View.GONE
                    binding.errorView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE

                }
                it.error != null -> {
                    binding.smsRV.visibility = View.GONE
                    binding.errorView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.updateAdapter()
                }
            }
        }
    }

    private fun ArrayList<Messages>.updateAdapter(){
        if (isEmpty()){
            binding.smsRV.visibility = View.GONE
            binding.errorView.visibility = View.VISIBLE
        } else {
            smsAdapter.diffUtil.submitList(this)
            binding.smsRV.visibility = View.VISIBLE
            binding.errorView.visibility = View.GONE
        }
    }

    private fun setRecycleView(){
        binding.smsRV.apply {
            smsAdapter = SMSAdapter()
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addItemDecoration(DividerItemDecoration(this@HomeActivity, LinearLayout.VERTICAL))
            adapter = smsAdapter
        }
    }

    private fun smsListener(){
        val client = SmsRetriever.getClient(this)
        val task: Task<Void> = client.startSmsRetriever()
        task.addOnSuccessListener {

        }

        task.addOnFailureListener {

        }
    }
}