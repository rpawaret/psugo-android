package com.ipoondev.android.psugo.quiz

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.ipoondev.android.psugo.R
import com.ipoondev.android.psugo.model.Quiz
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private lateinit var mFirestore: FirebaseFirestore
    private lateinit var quizList: ArrayList<Quiz>
    private lateinit var quiz: Quiz
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        quizList = ArrayList()

        FirebaseFirestore.getInstance().collection("quizzes")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            quiz = document.toObject(Quiz::class.java)
                            quizList.add(quiz)

                            Log.d(TAG, "${document.id} => ${document.data}")

                            setQuestionView(quiz)

                        }
                    }
                }

        button_quiz_next.setOnClickListener {
            checkAnswer()
        }

    }


    private fun setQuestionView(quiz: Quiz) {
        text_quiz_question.text = quiz.question
        radio_quiz_ch1.text = quiz.opt_1
        radio_quiz_ch2.text = quiz.opt_2
        radio_quiz_ch3.text = quiz.opt_3
        radio_quiz_ch4.text = quiz.opt_4
    }


    private fun checkAnswer() {
        val selecetedId = radio_group_answer.checkedRadioButtonId
        Log.d(TAG, "checked: ${selecetedId}")
        val answer = findViewById<RadioButton>(selecetedId)

        if (answer.text != null) {
            if (quiz.answer!! == answer.text) {
                score = score + 1
                Log.d(TAG, "score: ${score}")
                answer.setBackgroundColor(resources.getColor(R.color.green))
            }
        } else {
            Toast.makeText(this, "โปรดเลือกคำตอบ", Toast.LENGTH_LONG).show()
        }


    }


    companion object {
        val TAG = QuizActivity::class.simpleName
    }

}
