package com.rammstein.viewalltest.adapter

import com.airbnb.epoxy.EpoxyAdapter
import com.rammstein.viewalltest.adapter.models.GroupModel
import com.rammstein.viewalltest.dao.ContextDAO

/**
 * Created by klitaviy on 1/12/18-3:51 PM.
 */
class ExpandableItemsAdapter(groups: Array<ContextDAO>,
                             private val singleExpandedItemMode: Boolean = true,
                             private val listener: () -> Unit) : EpoxyAdapter() {

    private val adapterModelsCache: MutableList<ExpandableAdapterModel> = mutableListOf()

    init {
        prepareModels(groups.toMutableList(), adapterModelsCache)
    }

    private fun prepareModels(data: MutableList<ContextDAO>, subModelsList: MutableList<ExpandableAdapterModel>) {
        data.forEach { element ->
            val model = GroupModel({ group ->
                if (singleExpandedItemMode) {
                    subModelsList
                            .filter { model -> model.id() != group.id() }
                            .forEach { model -> if (model.isExpanded) onItemClick(model, false) }
                }
                onItemClick(group, true)
            })
            val modelSubItems = mutableListOf<ExpandableAdapterModel>()
            model.subItems = modelSubItems
            model.mContextDAO = element

            subModelsList.add(model)

            prepareModels(element.subItems ?: emptyList<ContextDAO>().toMutableList(), modelSubItems)
        }
    }

    fun bind() {
        adapterModelsCache.forEach { group ->
            addModel(group)
        }
    }

    private fun onItemClick(item: ExpandableAdapterModel, shouldUpdate: Boolean) {
        if (item.isExpanded) {
            hideAllItems(item.subItems ?: emptyList())
            item.isExpanded = false
        } else {
            showAllItems(item)
        }
        if (shouldUpdate) {
            notifyDataSetChanged()
            listener.invoke()
        }
    }

    private fun hideAllItems(list: Collection<ExpandableAdapterModel>) {
        list.forEach {
            hideAllItems(it.subItems ?: emptyList())
            it.isExpanded = false
            removeModel(it)
        }
    }

    private fun showAllItems(item: ExpandableAdapterModel) {
        var after = item
        item.isExpanded = true
        item.subItems?.forEach {
            insertModelAfter(it, after)
            after = it
        }
    }
}