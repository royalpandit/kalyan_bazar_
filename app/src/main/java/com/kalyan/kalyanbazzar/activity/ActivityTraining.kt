// TrainingActivity.kt
package com.kalyan.kalyanbazzar.activity

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalyan.kalyanbazzar.R
import com.kalyan.kalyanbazzar.adapter.TrainingAdapter
import com.kalyan.kalyanbazzar.databinding.ActivityTrainingBinding
import com.kalyan.kalyanbazzar.model.response.QuestionModel
import com.kalyan.kalyanbazzar.utils.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainingActivity : BaseActivity<ActivityTrainingBinding>() {
    private lateinit var adapter: TrainingAdapter
    private var questionList: ArrayList<QuestionModel> = ArrayList()

    override fun getLayoutResId(): Int = R.layout.activity_training

    override fun setupViews() {
        dataBinding.apply {
            toolbar.tvTitle.text = "Training"
            toolbar.ivWallet.visibility = View.GONE
            toolbar.tvcois.visibility = View.GONE
            toolbar.ivBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            loadQuestions()

            adapter = TrainingAdapter(questionList)

            rvQuestions.layoutManager = LinearLayoutManager(this@TrainingActivity)

            rvQuestions.adapter = adapter

            btnSubmit.setOnClickListener {
                AlertDialog.Builder(this@TrainingActivity).setTitle("Thank You")
                    .setMessage("Training completed successfully.").setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                        finish()
                    }.show()
            }
        }
    }

    override fun setupViewsOnResume() {

    }

    private fun loadQuestions() {
        val allQuestions = arrayListOf(
            QuestionModel("India ka national animal Tiger hai?"),
            QuestionModel("Earth gol hai?"),
            QuestionModel("Sun west se nikalta hai?"),
            QuestionModel("2 + 2 = 4 hota hai?"),
            QuestionModel("Taj Mahal India me hai?"),
            QuestionModel("Water ka formula H2O hai?"),
            QuestionModel("Delhi India ki capital hai?"),
            QuestionModel("Moon ek planet hai?"),
            QuestionModel("Flutter Google ka hai?"),
            QuestionModel("Java programming language hai?"),
            QuestionModel("Fish paani me rehti hai?"),
            QuestionModel("Cow ud sakti hai?"),
            QuestionModel("Cricket India me popular hai?"),
            QuestionModel("Android mobile OS hai?"),
            QuestionModel("Train hawa me chalti hai?")
        )

        questionList = ArrayList(allQuestions.shuffled().take(10))
    }
}