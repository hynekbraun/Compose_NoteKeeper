package com.hynekbraun.composenotekeeper.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hynekbraun.composenotekeeper.presentation.createnote.CreateNoteScreen
import com.hynekbraun.composenotekeeper.presentation.createnote.CreateNoteState
import com.hynekbraun.composenotekeeper.presentation.navigation.NoteNavArgs
import com.hynekbraun.composenotekeeper.presentation.navigation.NoteDestination
import com.hynekbraun.composenotekeeper.presentation.notedetail.EditNoteScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NoteDestination.NOTES_LIST_ROUTE) {
        composable(NoteDestination.NOTES_LIST_ROUTE) {
            NoteListScreen(
                viewModel = hiltViewModel(),
                onNoteClick = { id ->
                    navController.navigate("${NoteDestination.EDIT_NOTE_ROUTE}/$id")
                },
                onCreateClick = { navController.navigate(NoteDestination.CREATE_NOTE_ROUTE) }
            )
        }
        composable(
            route = "${NoteDestination.EDIT_NOTE_ROUTE}/{${NoteNavArgs.NOTE_ID}}",
            arguments = listOf(
                navArgument(NoteNavArgs.NOTE_ID) {
                    type = NavType.IntType
                    nullable = false
                }
            )) {
            EditNoteScreen(
                viewModel = hiltViewModel(),
                onEditClick = {
                    navController.navigate(NoteDestination.NOTES_LIST_ROUTE) {
                        popUpTo(NoteDestination.NOTES_LIST_ROUTE){inclusive = true}
                    }
                }
            )
        }
        composable(
            route = NoteDestination.CREATE_NOTE_ROUTE
        ) {
            CreateNoteScreen(viewModel = hiltViewModel(),
                onSaveClicked = {
                    navController.navigate(NoteDestination.NOTES_LIST_ROUTE) {
                        popUpTo(NoteDestination.NOTES_LIST_ROUTE){inclusive = true}
                    }
                })
        }

    }

}