package com.dantropov.medtest.ui.quiz

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dantropov.medtest.R
import com.dantropov.medtest.database.model.MedQuiz
import com.dantropov.medtest.databinding.FragmentQuizBinding
import com.dantropov.medtest.util.animation.TextViewAnimation
import com.dantropov.medtest.util.view.ViewBindingHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private val bindingHolder = ViewBindingHolder<FragmentQuizBinding>()
    private val binding get() = bindingHolder.binding
    private val viewModel: QuizViewModel by viewModels()
    private val args: QuizFragmentArgs by navArgs()
    private val optionsList: List<TextView> by lazy {
        listOf(
            binding.tvOption1,
            binding.tvOption2,
            binding.tvOption3,
            binding.tvOption4,
            binding.tvOption5
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = bindingHolder.createView(viewLifecycleOwner) {
        FragmentQuizBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(args.quizArg)
        setupUi()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is QuizUiState.Ready -> setupQuiz(uiState.medQuiz)
                        is QuizUiState.CorrectAnswer -> chooseCorrectAnswer(uiState.correctOrder)
                        is QuizUiState.WrongAnswer -> chooseWrongAnswer(uiState.wrongOrder, uiState.correctOrder)
                        is QuizUiState.NavigateToNextQuestion -> navigateToNextQuestion(uiState.quizLevelData)
                        is QuizUiState.Finish -> showFinishDialog(uiState.quizLevelData)
                        else -> {}
                    }
                }
            }
        }
    }

    private fun chooseWrongAnswer(wrongOrder: Int, correctOrder: Int) {
        val tvWrongAnswer = optionsList.getOrNull(wrongOrder) ?: return
        tvWrongAnswer.setBackgroundColor(requireContext().getColor(R.color.colorWrong))
        chooseCorrectAnswer(correctOrder)
    }

    private fun chooseCorrectAnswer(correctOrder: Int) {
        val tvCorrect = optionsList.getOrNull(correctOrder) ?: return
        TextViewAnimation.animateCorrectAnswer(tvCorrect)
    }

    private fun setupQuiz(medQuiz: MedQuiz) {
        if (medQuiz.answers.size == 5) {
            binding.tvQuestion.text = medQuiz.question
            binding.tvOption1.text = medQuiz.answers[0].text
            binding.tvOption2.text = medQuiz.answers[1].text
            binding.tvOption3.text = medQuiz.answers[2].text
            binding.tvOption4.text = medQuiz.answers[3].text
            binding.tvOption5.text = medQuiz.answers[4].text
        }
    }

    private fun setupUi() {
        binding.tvOption1.setOnClickListener(parentClickListener { viewModel.itemChoose(0) })
        binding.tvOption2.setOnClickListener(parentClickListener { viewModel.itemChoose(1) })
        binding.tvOption3.setOnClickListener(parentClickListener { viewModel.itemChoose(2) })
        binding.tvOption4.setOnClickListener(parentClickListener { viewModel.itemChoose(3) })
        binding.tvOption5.setOnClickListener(parentClickListener { viewModel.itemChoose(4) })
        binding.root.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_UP -> {
                    viewModel.nextQuiz(args.quizArg)
                    v.performClick()
                }
                else -> {}
            }
            v.onTouchEvent(event)
        }
        binding.root.setOnClickListener {
            viewModel.nextQuiz(args.quizArg)
        }
    }

    private fun navigateToNextQuestion(quizLevelData: QuizLevelData) {
        findNavController().navigate(QuizFragmentDirections.actionQuizFragmentSelf(quizLevelData))
    }

    private fun showFinishDialog(data: QuizLevelData) {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle("Congratulations!")
            setMessage("You finished current training: ${data.rightAnswersCount} / ${data.endQuestionId - data.startQuestionId} correct answers")
            setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        }
        builder.show()
    }

    private fun parentClickListener(clickListener: () -> Unit): View.OnClickListener = View.OnClickListener { v ->
        binding.root.performClick()
        clickListener()
    }
}