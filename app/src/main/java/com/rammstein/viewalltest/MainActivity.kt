package com.rammstein.viewalltest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.rammstein.viewalltest.adapter.ExpandableItemsAdapter
import com.rammstein.viewalltest.dao.ContextDAO
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Created by klitaviy on 1/12/18-5:53 PM.
 */
class MainActivity : AppCompatActivity() {

    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewAllContent.layoutManager = LinearLayoutManager(this)
        val data = getViewAllData()
        val adapter = ExpandableItemsAdapter(data.toTypedArray(), true, { viewAllContent.recycledViewPool.clear() })
        viewAllContent.adapter = adapter
        adapter.bind()
    }

    private fun getViewAllData(): MutableList<ContextDAO> {

        val groupsCount = 4
        val regionsCount = 4
        val branchesCount = 4
        val communitiesCount = 1
        val groups = mutableListOf<ContextDAO>()

        for (g in 1..groupsCount) {
            val groupModel = getGroup(g)
            log(g)
            val regionsList = mutableListOf<ContextDAO>()

            groupModel.subItems = regionsList

            for (r in 1..regionsCount) {
                val regionModel = getRegion(r, g)
                log(g, r)
                val branchesList = mutableListOf<ContextDAO>()

                regionModel.subItems = branchesList

                regionsList.add(regionModel)

                for (b in 1..branchesCount) {
                    val branchModel = getBranch(b, g, r)
                    log(g, r, b)
                    val communitiesList = mutableListOf<ContextDAO>()

                    branchModel.subItems = communitiesList

                    branchesList.add(branchModel)

                    for (c in 1..communitiesCount) {
                        val communitiesModel = getCommunity(c, g, r, b)
                        log(g, r, b, c)

                        communitiesModel.subItems = mutableListOf()

                        communitiesList.add(communitiesModel)

                    }
                }
            }
            groups.add(groupModel)
        }

        return groups
    }

    private fun getGroup(index: Int) = getViewAllDAO("Group : ".plus(index))
    private fun getRegion(index: Int, groupId: Int) = getViewAllDAO("G : ".plus(groupId).plus(" : Region : ".plus(index)))
    private fun getBranch(index: Int, groupId: Int, regionId: Int) = getViewAllDAO("G : ".plus(groupId).plus(" : R : ".plus(regionId).plus(" : Branch : ".plus(index))))
    private fun getCommunity(index: Int, groupId: Int, regionId: Int, branchId: Int) = getViewAllDAO("G : ".plus(groupId).plus(" : R : ".plus(regionId).plus(" : B : ").plus(branchId).plus(" : Community : ".plus(index))))

    private fun getViewAllDAO(name: String): ContextDAO {
        val viewAllDao = ContextDAO()
        viewAllDao.name = name
        viewAllDao.id = random.nextInt(99000).toLong()

        return viewAllDao
    }

    private fun log(g: Int, r: Int = 0, b: Int = 0, c: Int = 0) {
        Log.d("creating_deb", "G : ".plus(g).plus(" : R : ".plus(r).plus(" : B : ".plus(b).plus(" : C : ".plus(c)))))
    }
}