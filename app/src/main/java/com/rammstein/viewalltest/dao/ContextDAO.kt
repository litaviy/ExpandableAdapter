package com.rammstein.viewalltest.dao

/**
 * Created by klitaviy on 1/12/18-3:31 PM.
 */
class ContextDAO {
    var subItems: MutableList<ContextDAO>? = null
    var name: String? = null
    var mParent: ContextDAO? = null
    var id : Long? = null
}