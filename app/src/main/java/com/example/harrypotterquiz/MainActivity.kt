package com.example.harrypotterquiz

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.harrypotterquiz.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var rgAnswers: RadioGroup
    private lateinit var btnNext: Button
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvQuestion = findViewById(R.id.tvQuestion)
        rgAnswers = findViewById(R.id.rgAnswers)
        btnNext = findViewById(R.id.btnNext)

        // Carrega as perguntas do QuizData.kt
        val questions = getQuizQuestions()

        // Define a primeira questão
        updateQuestion(questions[currentQuestionIndex])

        btnNext.setOnClickListener {
            // Verifica a resposta selecionada
            val selectedAnswerId = rgAnswers.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedAnswerId)
            val selectedAnswer = selectedRadioButton.text.toString()

            // Verifica se a resposta está correta
            if (selectedAnswer == questions[currentQuestionIndex].correctAnswer) {
                score++
            }

            // Avança para a próxima questão ou mostra o resultado
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                updateQuestion(questions[currentQuestionIndex])
            } else {
                showResult()
            }
        }
    }

    private fun updateQuestion(question: Question) {
        tvQuestion.text = question.text
        rgAnswers.removeAllViews()
        for (option in question.options) {
            val radioButton = RadioButton(this)
            radioButton.text = option
            rgAnswers.addView(radioButton)
        }
    }

    private fun showResult() {
        val message = "Sua pontuação final: $score de ${getQuizQuestions().size}"
        AlertDialog.Builder(this)
            .setTitle("Fim do Quiz")
            .setMessage(message)
            .setPositiveButton("Reiniciar") { dialog, _ ->
                dialog.dismiss()
                // Reinicia o quiz ao clicar em OK
                currentQuestionIndex = 0
                score = 0
                updateQuestion(getQuizQuestions()[currentQuestionIndex])
            }
            .show()
    }
}

// Perguntas

data class Question(val text: String, val options: List<String>, val correctAnswer: String, val index: Int)

data class QuizData(val questions: List<Question>)

fun getQuizQuestions(): List<Question> {
    val quizData = QuizData(
        questions = listOf(
            Question(
                "Qual é o nome da escola de magia que Harry frequenta?",
                listOf("Hogwarts", "Durmstrang", "Beauxbatons"),
                "Hogwarts",
                0
            ),
            Question(
                "Quem é o aluno mais famoso da Sonserina",
                listOf("Cedrico Diggory", "Severo Snape", "Draco Malfoy"),
                "Draco Malfoy",
                1
            ),
            Question(
                "Qual desses personagens não pertence à Grifinória?",
                listOf("Neville Longbottom", "Hermione Granger", "Luna Lovegood"),
                "Luna Lovegood",
                2
            ),
            Question(
                "Qual dessas qualidades é mais valorizada na casa Corvinal?",
                listOf("Coragem", "Inteligência", "Lealdade"),
                "Inteligência",
                3
            ),
            Question(
                "Qual objeto é essencial para ganhar o torneio Tribruxo",
                listOf("A Varinha das Varinhas", "O Cálice de Fogo", "A Pedra Filosofal"),
                "O Cálice de Fogo",
                4
            ),
            Question(
                "Quem é a fundadora da casa Lufa-Lufa?",
                listOf("Helga Hufflepuff", "Rowena Ravenclaw", "Salazar Slytherin"),
                "Helga Hufflepuff",
                5
            ),
            Question(
                "Qual é o objeto que pertenceu à fundadora da casa Corvinal, Rowena Ravenclaw?",
                listOf("Uma espada", "Um medalhão", "Uma tiara"),
                "Uma tiara",
                6
            ),
            Question(
                "Quem foi o diretor de Hogwarts que pertencia à Sonserina?",
                listOf("Minerva McGonagall", "Gilderoy Lockhart", "Severo Snape"),
                "Severo Snape",
                7
            ),
            Question(
                "Qual criatura ajuda Draco Malfoy a reparar o Armário Sumidouro",
                listOf("Um elfo doméstico", "Um trasgo" ,"Um fantasma"),
                "Um elfo doméstico",
                8
            ),
            Question(
                "Quem é o aluno de Corvinal que ajuda Harry Potter a encontrar a Sala Precisa?",
                listOf("Cho Chang", "Padma Patil", "Luna Lovegood"),
                "Luna Lovegood",
                9
            ),
            // Adicione mais perguntas aqui
        )
    )
    return quizData.questions
}