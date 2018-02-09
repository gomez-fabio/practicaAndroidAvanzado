package es.fabiogomez.repository.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
internal class ShopsResponseEntity ( var result : List<ShopEntity> )
