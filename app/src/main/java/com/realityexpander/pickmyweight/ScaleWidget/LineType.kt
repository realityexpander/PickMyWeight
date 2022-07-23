package com.realityexpander.pickmyweight.ScaleWidget

sealed class LineType {
    object Normal: LineType()
    object FiveStep: LineType()
    object TenStep: LineType()
}
