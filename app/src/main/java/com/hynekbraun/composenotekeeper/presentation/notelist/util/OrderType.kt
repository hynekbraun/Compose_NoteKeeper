package com.hynekbraun.composenotekeeper.presentation.notelist.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
