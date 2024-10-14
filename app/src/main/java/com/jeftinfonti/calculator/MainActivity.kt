package com.jeftinfonti.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jeftinfonti.calculator.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var op = ""
    private var oldNumber = ""
    private var number = ""
    private var isNewOp = true
    private val df = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNum0.setOnClickListener { numberEvent("0") }
        binding.btnNum1.setOnClickListener { numberEvent("1") }
        binding.btnNum2.setOnClickListener { numberEvent("2") }
        binding.btnNum3.setOnClickListener { numberEvent("3") }
        binding.btnNum4.setOnClickListener { numberEvent("4") }
        binding.btnNum5.setOnClickListener { numberEvent("5") }
        binding.btnNum6.setOnClickListener { numberEvent("6") }
        binding.btnNum7.setOnClickListener { numberEvent("7") }
        binding.btnNum8.setOnClickListener { numberEvent("8") }
        binding.btnNum9.setOnClickListener { numberEvent("9") }

        binding.btnSum.setOnClickListener { operationEvent("+") }
        binding.btnSubtr.setOnClickListener { operationEvent("-") }
        binding.btnMult.setOnClickListener { operationEvent("*") }
        binding.btnDiv.setOnClickListener { operationEvent("/") }

        binding.btnEqual.setOnClickListener { resultEvent() }

        binding.btnClear.setOnClickListener {
            binding.textResult.text = "0"
            isNewOp = true
            number = ""
            oldNumber = ""
            op = ""
        }
    }

    private fun resultEvent(){
        val newNumber = binding.textResult.text.toString()
        var result = 0.0

        if (newNumber.isNotEmpty()) {
            try {
                val num1 = oldNumber.toDouble()
                val num2 = newNumber.toDouble()

                when (op) {
                    "+" -> result = num1 + num2
                    "-" -> result = num1 - num2
                    "*" -> result = num1 * num2
                    "/" -> {
                        if (num2 != 0.0) {
                            result = num1 / num2
                        } else {

                            binding.textResult.text = "0"
                            Toast.makeText(this, R.string.validation_no_div_zero, Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
                }

                binding.textResult.text = df.format(result)
                oldNumber = df.format(result)

            } catch (e: NumberFormatException) {
                binding.textResult.text = "0"
            }
        }
    }

    private fun numberEvent(num: String) {
        if (isNewOp || binding.textResult.text == "0") {
            binding.textResult.text = ""
        }
        isNewOp = false
        number = binding.textResult.text.toString() + num
        binding.textResult.text = number
    }

    private fun operationEvent(op: String) {
        if (number != "" && oldNumber != ""){
            resultEvent()
            oldNumber = ""
        }
        oldNumber = binding.textResult.text.toString()
        isNewOp = true
        this.op = op
    }
}