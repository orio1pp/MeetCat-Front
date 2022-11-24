package com.pes.meetcatui.feature_event.presentation

import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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
        if (name != "" && subtitle != "" && description != "" && startDate != "" && endDate != "" && location != "" && place != "" && address != "" && link != "")
        {
            viewModelScope.launch {
                val event = Event(0, name, subtitle, description, startDate, endDate, location, place, address, link)
                dataRepository.createEvent( event )
            }
            return true;
        } else
            return false;

    }


}