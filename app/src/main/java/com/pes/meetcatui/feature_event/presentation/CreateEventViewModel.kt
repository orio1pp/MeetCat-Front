package com.pes.meetcatui.feature_event.presentation

import android.webkit.URLUtil
import androidx.compose.ui.res.stringResource
import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pes.meetcatui.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*

class CreateEventViewModel (
    dataRepository : DataRepository
        ) : ViewModel() {

    val dataRepository = dataRepository

    fun createEvent(
        name: String, subtitle: String, description: String,
        startDate: String, endDate: String, location:String, place: String,
        address: String, link: String
    ) : Boolean {
        viewModelScope.launch {
            val event = Event(0, name, subtitle, description, startDate, endDate, location, place, address, link, 0)
            dataRepository.createEvent( event )
        }
        return true;

    }


}