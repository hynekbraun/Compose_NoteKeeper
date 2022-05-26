package com.hynekbraun.composenotekeeper.presentation.notelist.util

sealed class NoteOrder(val orderType: OrderType) {
    class Header(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)

    fun copy(orderType: OrderType): NoteOrder {
        return when(this) {
            is Header -> Header(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}