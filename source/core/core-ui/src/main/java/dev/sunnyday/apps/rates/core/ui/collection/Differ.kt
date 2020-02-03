package dev.sunnyday.apps.rates.core.ui.collection

import androidx.recyclerview.widget.DiffUtil

interface Differ<T> {

    fun forceContentNotTheSameOnNextDiff()

    fun calculateDiff(
        old: List<T>,
        new: List<T>
    ): DiffUtil.DiffResult

}

abstract class SimpleDiffer<T> : Differ<T> {

    private var isForceContentNotTheSameOnNextDiff = false

    override fun forceContentNotTheSameOnNextDiff() {
        isForceContentNotTheSameOnNextDiff = true
    }

    override fun calculateDiff(
        old: List<T>,
        new: List<T>
    ): DiffUtil.DiffResult {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size

            override fun areItemsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean = areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean = !isForceContentNotTheSameOnNextDiff &&
                    areContentsTheSame(old[oldItemPosition], new[newItemPosition])

        })

        isForceContentNotTheSameOnNextDiff = false

        return result
    }

    protected abstract fun areItemsTheSame(old: T, new: T): Boolean

    protected abstract fun areContentsTheSame(old: T, new: T): Boolean

}