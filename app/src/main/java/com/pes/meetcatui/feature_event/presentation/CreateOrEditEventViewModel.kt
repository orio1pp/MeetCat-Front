package com.pes.meetcatui.feature_event.presentation

import com.pes.meetcatui.feature_event.domain.DataRepository
import com.pes.meetcatui.feature_event.domain.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CreateOrEditEventViewModel (
    dataRepository : DataRepository
) : ViewModel() {

    val dataRepository = dataRepository

    fun createEvent(
        name: String, subtitle: String, description: String,
        startDate: String, endDate: String, location:String, place: String,
        address: String, link: String
    ) : Boolean {
        viewModelScope.launch {
            val event = Event(0, name, subtitle, null, description, startDate, endDate, location, place, address, link, 0)
            dataRepository.createEvent( event )
        }
        return true;

    }

    fun updateEvent(
        id: Long,
        name: String,
        subtitle: String,
        description: String,
        startDate: String,
        endDate: String,
        location:String,
        place: String,
        address: String,
        link: String,
        attendeesCount: Int,
    ) : Boolean {
        viewModelScope.launch {
            val event = Event(
                eventId = id,
                name = name,
                subtitle = subtitle,
                username = null,
                description = description,
                startDate = startDate,
                endDate = endDate,
                location = location,
                placeName = place,
                address = address,
                link = link,
                attendeesCount = attendeesCount)
            dataRepository.updateEvent(event)
        }
        return true;

    }

}