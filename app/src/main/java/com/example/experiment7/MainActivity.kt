package com.example.experiment7

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var msgField:EditText
    private lateinit var phoneField:EditText
    private lateinit var sendButton: Button
    private val SMSpermissionRequest = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        msgField=findViewById(R.id.msg)
        phoneField=findViewById(R.id.phone)
        sendButton=findViewById(R.id.send)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
            SMSpermissionRequest)
        sendButton.setOnClickListener {
            val phone=phoneField.text.toString()
            val msg=msgField.text.toString()
            if(TextUtils.isEmpty(phone) && TextUtils.isEmpty(msg)){
                Toast.makeText(this,"Empty Fields",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                sendMsg(phone,msg)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                    SMSpermissionRequest)
            }

            phoneField.setText("")
            msgField.setText("")
        }

    }

    fun sendMsg(phone:String,msg:String){
        if (TextUtils.isDigitsOnly(phone) && phone!!.length == 10) {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phone, null, msg, null, null)
            Toast.makeText(this, "message Sent to number $phone", Toast.LENGTH_SHORT).show()
            Log.d( "Message Sent","yes");
        } else {
            Log.d( "Message Sent","No ");
            Toast.makeText(this, "Please enter the correct number", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults:
    IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMSpermissionRequest) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "You don't have required permission to send a message",
                    Toast.LENGTH_SHORT).show();
                finishActivity(0)
            }
        }
    }
}