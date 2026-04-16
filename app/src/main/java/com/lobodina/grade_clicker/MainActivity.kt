package com.lobodina.grade_clicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lobodina.grade_clicker.data.Datasource
import com.lobodina.grade_clicker.model.Grade
import com.lobodina.grade_clicker.ui.theme.GradeClickerTheme

//import android.os.Bundle
//import android.view.SurfaceControl
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.materialIcon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.layout.ModifierInfo
//import androidx.compose.ui.res.dimensionResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.clickable
//import androidx.compose.material3.Surface
//import com.lobodina.grade_clicker.data.Datasource
//import com.lobodina.grade_clicker.model.Grade
//import com.lobodina.grade_clicker.ui.theme.GradeClickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GradeClickerTheme {
                Surface(
                    modifier= Modifier.fillMaxSize()
                ) {
                    GradeClickerApp(grades= Datasource.gradeList)
                }
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
        Text(
            text= stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(currentGrade.imageId),
            contentDescription = null,
            modifier = Modifier
                .size(dimensionResource(R.dimen.image_size))
                .clickable {
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
    Column(
        modifier=modifier
            .fillMaxWidth()
            .padding()
    ){
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = stringResource(R.string.points_earned),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = points.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(
            modifier=Modifier
        ){
            Text(
                text = stringResource(R.string.total_clicks),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = clicks.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
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
@Preview
@Composable
fun GradeClickerPreview(){
    GradeClickerTheme{
        GradeClickerApp(grades = Datasource.gradeList)
    }
}