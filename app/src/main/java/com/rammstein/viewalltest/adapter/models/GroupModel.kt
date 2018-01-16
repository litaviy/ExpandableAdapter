package com.rammstein.viewalltest.adapter.models

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.rammstein.viewalltest.R
import com.rammstein.viewalltest.adapter.ExpandableAdapterModel

/**
 * Created by klitaviy on 1/12/18-3:56 PM.
 */
class GroupModel(listener: (ExpandableAdapterModel) -> Unit) : ExpandableAdapterModel(listener) {

    override fun createNewHolder() = GroupHolder()

    override fun getDefaultLayout() = R.layout.group_item_layout

    override fun bind(holder: ViewAllAdapterEpoxyHolder) {
        super.bind(holder)
        holder.bind(mContextDAO?.name ?: "", isExpanded)
    }

    override fun id() = mContextDAO!!.id!!

    inner class GroupHolder : ViewAllAdapterEpoxyHolder() {

        private lateinit var title: TextView
        private lateinit var selectionView: View

        override fun bindView(itemView: View) {
            super.bindView(itemView)
            title = itemView.findViewById(R.id.groupTitle)
            selectionView = itemView.findViewById(R.id.groupIsSelected)
        }

        override fun bind(name: String, isSelected: Boolean) {
            title.text = name
            val color = if (!isSelected) {
                android.R.color.transparent
            } else {
                R.color.unselected_color
            }
            selectionView.setBackgroundColor(ContextCompat.getColor(selectionView.context, color))
        }
    }
}