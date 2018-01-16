package com.rammstein.viewalltest.adapter

import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.rammstein.viewalltest.dao.ContextDAO

/**
 * Created by klitaviy on 1/12/18-3:34 PM.
 */
abstract class ExpandableAdapterModel(private val listener: (ExpandableAdapterModel) -> Unit) : EpoxyModelWithHolder<ExpandableAdapterModel.ViewAllAdapterEpoxyHolder>() {

    var subItems: MutableList<ExpandableAdapterModel>? = null
    var mContextDAO: ContextDAO? = null
    var isExpanded: Boolean = false

    abstract inner class ViewAllAdapterEpoxyHolder : EpoxyHolder() {
        override fun bindView(itemView: View) {
            itemView.setOnClickListener { listener.invoke(this@ExpandableAdapterModel) }
        }

        abstract fun bind(name: String, isSelected: Boolean)
    }
}