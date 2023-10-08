package com.starwars.app.character.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.starwars.app.character.domain.entity.Character

@Composable
fun CharacterItem(character: Character){
    Box(modifier = Modifier.padding(top = 8.dp)) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = character.name!!,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = character.birthYear!!,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = character.gender!!,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

}