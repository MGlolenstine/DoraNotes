package com.example.doranotes.Screens.HomeScreen

import android.provider.ContactsContract
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.doranotes.MainViewModel.MainViewModel
import com.example.doranotes.Model.Note
import com.example.doranotes.Navigation.NavRoutes
import com.example.doranotes.components.NoteRow


@Composable
fun HomeScreen(viewModel: MainViewModel, navHostController: NavHostController) {
    val l = viewModel.getAllNote().observeAsState().value

    val config = LocalConfiguration.current
    val height = config.screenHeightDp.dp

    val value = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.background(color = Color(0xff57D8FF))) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(height / 8).background(color = Color(0xff57D8FF)).clip(shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.TopCenter
        ) {

            OutlinedTextField(value = value.value, onValueChange = { value.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                textStyle = TextStyle(fontSize = 23.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(backgroundColor = Color(
                    0xFF00C4FF)),
                placeholder = { Text(text = "Search") },
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {}),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = null)
                }
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {

            if (l != null) {
                LazyColumn {
                    items(l) { note ->
                        NoteRow(height = height, note = note, onClick = {
                            viewModel.currentNote = note
                            navHostController.navigate("display_note/" + note.Id)
                        }) {
                            viewModel.delete(note)
                        }
                    }
                }
            }

            FloatingActionButton(onClick = { navHostController.navigate(NavRoutes.AddNote.route) },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 20.dp)) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp))
            }
        }
    }
}

