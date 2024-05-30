package com.example.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        const val BUTTON_AC = "AC"
        const val BUTTON_BACKSPACE = "⌫"
        const val BUTTON_PERCENTAGE = "%"
        const val BUTTON_DIVIDE = "÷"
        const val BUTTON_MULTIPLY = "×"
        const val BUTTON_MINUS = "−"
        const val BUTTON_PLUS = "+"
        const val BUTTON_EQUALS = "="
        const val BUTTON_HISTORY = "⟳"
        const val BUTTON_DOT = "."
        const val BUTTON_0 = "0"
        const val BUTTON_1 = "1"
        const val BUTTON_2 = "2"
        const val BUTTON_3 = "3"
        const val BUTTON_4 = "4"
        const val BUTTON_5 = "5"
        const val BUTTON_6 = "6"
        const val BUTTON_7 = "7"
        const val BUTTON_8 = "8"
        const val BUTTON_9 = "9"
    }

    private val _displayText = MutableLiveData("0")
    val displayText: LiveData<String> get() = _displayText

    private var currentNumber = ""
    private var operation = ""
    private var firstNumber = ""
    private val history = mutableListOf<String>()

    fun onButtonClick(button: String) {
        when (button) {
            BUTTON_AC -> clear()
            BUTTON_BACKSPACE -> backspace()
            BUTTON_PERCENTAGE -> setOperation("%")
            BUTTON_DIVIDE -> setOperation("/")
            BUTTON_MULTIPLY -> setOperation("*")
            BUTTON_MINUS -> setOperation("-")
            BUTTON_PLUS -> setOperation("+")
            BUTTON_EQUALS -> calculate()
            BUTTON_HISTORY -> showHistory()
            else -> numberClick(button)
        }
    }

    private fun numberClick(number: String) {
        currentNumber += number
        _displayText.value = currentNumber
    }

    private fun setOperation(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber
            currentNumber = ""
            operation = op
            _displayText.value = ""
        }
    }

    private fun calculate() {
        if (currentNumber.isNotEmpty() && firstNumber.isNotEmpty()) {
            val result = when (operation) {
                "+" -> firstNumber.toDouble() + currentNumber.toDouble()
                "-" -> firstNumber.toDouble() - currentNumber.toDouble()
                "*" -> firstNumber.toDouble() * currentNumber.toDouble()
                "/" -> firstNumber.toDouble() / currentNumber.toDouble()
                "%" -> firstNumber.toDouble() % currentNumber.toDouble()
                else -> 0.0
            }

            history.add("$firstNumber $operation $currentNumber = $result")
            _displayText.value = result.toString()
            firstNumber = result.toString()
            currentNumber = ""
        }
    }

    private fun clear() {
        currentNumber = ""
        firstNumber = ""
        operation = ""
        _displayText.value = "0"
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            _displayText.value = currentNumber
        }
    }

    private fun showHistory() {
        val historyText = history.joinToString("\n")
        _displayText.value = historyText
    }
}
