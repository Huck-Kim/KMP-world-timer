
package org.crevolika.kmp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp0.composeapp.generated.resources.*
import kmp0.composeapp.generated.resources.Res
import kmp0.composeapp.generated.resources.fr
import kmp0.composeapp.generated.resources.kr
import kmp0.composeapp.generated.resources.mx
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class Country(val name: String, val zone: TimeZone, val image: DrawableResource)

fun currentTimeAt(location: String, zone: TimeZone): String {
    fun LocalTime.formatted() = "$hour:$minute:$second"

        val time = Clock.System.now()
        val localTime = time.toLocalDateTime(zone).time
        return "The time in $location is ${ localTime.formatted()}"

}

fun countries() = listOf(
    Country("Seoul", TimeZone.of("Asia/Seoul"), Res.drawable.kr),
    Country("France", TimeZone.of("Europe/Paris"),Res.drawable.fr),
    Country("Mexico", TimeZone.of("America/Mexico_City"),Res.drawable.mx),
    Country("Indonesia", TimeZone.of("Asia/Jakarta"),Res.drawable.id),
    Country("Egypt", TimeZone.of("Africa/Cairo"),Res.drawable.eg),
)


@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContries by remember { mutableStateOf(false) }
        var timeAtLocation by remember { mutableStateOf("No location selected") }
        var selectedZone by remember { mutableStateOf<TimeZone?>(null) }

        LaunchedEffect(selectedZone) {
            while (true) { // Time goes up every second
                if (selectedZone != null) {
                    timeAtLocation = currentTimeAt("Location", selectedZone?: TimeZone.currentSystemDefault())
                }
                delay(1000L)
            }
        }

        Column {
            Text(timeAtLocation,
                 style = TextStyle(fontSize = 20.sp),
                 textAlign = TextAlign.Center,
                 modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
            )
            Row(modifier = Modifier.padding(start = 20.dp, top = 10.dp)) {
                DropdownMenu(
                    expanded = showContries,
                    onDismissRequest = { showContries = false }
                ) {
                    countries().forEach { (name, zone, image) ->
                        DropdownMenuItem(
                            onClick = {
                                selectedZone = zone
                                timeAtLocation = currentTimeAt(name,zone)
                                showContries = false
                            }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painterResource(image),
                                    modifier =Modifier.size(50.dp).padding(end = 10.dp),
                                    contentDescription = "$name flag"
                                )
                                Text(name)
                            }
                        }
                    }
                }
            }
                Button(modifier = Modifier.padding(start = 10.dp),
                       onClick = { showContries = true }) {
                    Text("Select Location ")
                }

            }
        }
    }





/*
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Today's date is ${ todaysDate()}",
            modifier = Modifier.padding(20.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

fun todaysDate(): String {
    fun LocalDateTime.format() = toString().substringBefore('T')

    val now = Clock.System.now()
    val zone = TimeZone.currentSystemDefault()
    return now.toLocalDateTime(zone).format()
}
*/
