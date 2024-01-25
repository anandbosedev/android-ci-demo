package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = rememberCounterState(initialValue = 0)
                    Counter(state = state, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

class CounterState(initialValue: Int) {
    var count by mutableIntStateOf(initialValue)

    fun increment() {
        count += 1
    }

    fun decrement() {
        count -= 1
    }

    companion object {
        val Saver: Saver<CounterState, Int> = Saver(
            save = { it.count },
            restore = { CounterState(it) }
        )
    }
}

@Composable
fun rememberCounterState(initialValue: Int): CounterState {
    return rememberSaveable(saver = CounterState.Saver) {
        CounterState(initialValue)
    }
}
@Composable
fun Counter(modifier: Modifier = Modifier, state: CounterState) {
    Column(modifier, verticalArrangement = Arrangement.Center) {
        Text(
            text = state.count.toString(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .semantics { contentDescription = "CounterText" },
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 64.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = { state.increment() }) {
                Text(text = "Increment")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { state.decrement() }) {
                Text(text = "Decrement")
            }
        }
    }
}

@Preview
@Composable
fun CounterPreview() {
    val state = rememberCounterState(initialValue = 0)
    Counter(state = state)
}