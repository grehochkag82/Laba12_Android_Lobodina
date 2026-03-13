package com.lobodina.grade_clicker

import android.media.Image
import android.os.Bundle
import android.view.SurfaceControl
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lobodina.grade_clicker.model.Grade
import com.lobodina.grade_clicker.ui.theme.GradeClickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GradeClickerTheme {

                }
            }
        }
    }

@Composable
fun GradeClickerApp(grades: List<Grade>){
    var points by rememberSaveable { mutableStateOf(0) }
    var clicks by rememberSaveable {mutableStateOf (0)}
    val currentGrade = determineGradeToShow(grades, points)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        val materialTheme = null
        Text(
            text= stringResource(R.string.app_name),
            style = materialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(currentGrade.imageId),
            countenrDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size))
                .clickadle {
                    points += currentGrade.pointPerClick
                    clicks++
                },
            contentScale = ContentScale.Fit
        )
        TransactionInfo(
            points = points,
            clicks = clicks
        )
    }
}

@Composable
fun TransactionInfo(
    points: Int,
    clicks: Int,
    modifier: Modifier= Modifier
) {
    Column(){
        Row(){

        }
    }
}

fun determineGradeToShow(grades: List<Grade>, points: Int): Grade {
    var gradeToShow = grades.first()
    for (grade in grades){
        if (points>= grade.threshold){
            gradeToShow = grade
        }else{
            break
        }
    }
    return gradeToShow
}
