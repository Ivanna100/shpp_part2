package com.example.task_3.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

fun setTouchCallBackListener(
    onSwiped: (Int) -> Unit,
    isSwipeEnabled: () -> Boolean
): ItemTouchHelper.Callback {
    return object: ItemTouchHelper.SimpleCallback(0,0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun getSwipeDirs(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return ItemTouchHelper.LEFT
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwiped(viewHolder.bindingAdapterPosition)
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return isSwipeEnabled()
        }
    }
}