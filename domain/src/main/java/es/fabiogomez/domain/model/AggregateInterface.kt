package es.fabiogomez.domain.model

// Inteface segregation

interface ReadAggregate <T> {
    fun count(): Int
    fun All() : List<T>
    fun get(position: Int) : T
}

interface WriteAggregate<T> {
    fun add(element: T)
    fun delete(position: Int)
    fun delete(element: T)
}

interface Aggregate<T>: ReadAggregate<T>, WriteAggregate<T>