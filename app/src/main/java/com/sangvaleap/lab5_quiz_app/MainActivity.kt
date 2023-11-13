package com.sangvaleap.lab5_quiz_app

import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.sangvaleap.lab5_quiz_app.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var totalScore: Int = 0
    private var quiz: ArrayList<MCQuestion> = arrayListOf(
        MCQuestion("Which of the following is the largest planet in the solar system?", listOf("Earth", "Mars", "Jupiter", "Venus"), 2),
        MCQuestion("Which of the following is the longest river in the world?", listOf("Amazon River", "Nile River", "Yangtze River", "Mississippi River"), 1),
    )
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initQuestions()

        binding.btnSubmit.setOnClickListener { submit() }

        binding.btnReset.setOnClickListener { reset() }

    }

    private fun initQuestions(){
        var mcq = quiz[0]
        binding.tvQ1.text = mcq.question
        binding.rd1.text = mcq.options[0]
        binding.rd2.text = mcq.options[1]
        binding.rd3.text = mcq.options[2]
        binding.rd4.text = mcq.options[3]

        mcq = quiz[1]
        binding.tvQ2.text = mcq.question
        binding.cb1.text = mcq.options[0]
        binding.cb2.text = mcq.options[1]
        binding.cb3.text = mcq.options[2]
        binding.cb4.text = mcq.options[3]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submit(){
        var selectedIndexQ1 = binding.radioGroup.indexOfChild(findViewById(binding.radioGroup.checkedRadioButtonId))
        val selectedIndexQ2 = listOf(binding.cb1, binding.cb2, binding.cb3, binding.cb4)
            .indexOfFirst { it.isChecked }
            .takeIf { it != -1 } ?: -1
        calculateScore(listOf(selectedIndexQ1, selectedIndexQ2))
        showAlertDialog()
    }

    private fun calculateScore(selectedOptions: List<Int>){
        totalScore = 0
        if(selectedOptions[0] == quiz[0].correctOption) totalScore += 50
        if(selectedOptions[1] == quiz[1].correctOption) totalScore += 50
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(): String {
        val currentDateTime: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")
        return currentDateTime.format(formatter)
    }

    private fun reset(){
        binding.radioGroup.clearCheck()
        binding.cb1.isChecked = false
        binding.cb2.isChecked = false
        binding.cb3.isChecked = false
        binding.cb4.isChecked = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showAlertDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("Submission")
        var dateTime = getCurrentDateTime()
        alertDialogBuilder.setMessage("“Congratulations! You submitted on $dateTime, you achieved $totalScore%")

        alertDialogBuilder.setPositiveButton(android.R.string.ok) { _, _ -> }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}