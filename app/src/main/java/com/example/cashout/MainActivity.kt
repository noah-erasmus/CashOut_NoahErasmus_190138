package com.example.cashout

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor =  sharedPref.edit()

        val lastStore = sharedPref.getString(Constants.STORE_NAME, "No expenses yet")
        val totalExpenses = sharedPref.getInt(Constants.TOTAL_EXPENSES, 0)

        tv_amount.text = totalExpenses.toString()
        tv_last.text = lastStore

        btn_add.setOnClickListener{
            if(et_name.text.toString().isEmpty()){
                Toast.makeText(this , "Please the store name", Toast.LENGTH_SHORT).show()
            }else if(et_amount.equals(0)){
                Toast.makeText(this , "Please enter the amount", Toast.LENGTH_SHORT).show()
            }else{
                editor.apply{
                    var newTotal : Int = sharedPref.getInt(Constants.TOTAL_EXPENSES, 0) + et_amount.text.toString().toInt()

                    putString(Constants.STORE_NAME, "Last amount spent at " + et_name.text.toString())
                    putInt(Constants.TOTAL_EXPENSES, newTotal)
                    apply()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btn_refresh.setOnClickListener{
            editor.clear()
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}