package es.fabiogomez.repository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
internal class ActivitiesResponseEntity(var result : List<ActivityEntity> )
