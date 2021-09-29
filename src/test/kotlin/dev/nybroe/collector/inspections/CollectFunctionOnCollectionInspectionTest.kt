package dev.nybroe.collector.inspections

import com.intellij.codeInspection.InspectionProfileEntry

internal class CollectFunctionOnCollectionInspectionTest : InspectionTest() {
    override fun defaultInspection(): InspectionProfileEntry = CollectFunctionInCollectionInspection()
    override fun defaultAction(): String = ""

    fun testCollectOnCollectVariable() {
        doTest("collect_on_collect_variable")
    }

    fun testCollectInCollect() {
        doTest("collect_in_collect")
    }

    fun testCollectOnEloquentCollectVariable() {
        doTest("collect_on_eloquent_collect_variable")
    }

    fun testCollectOnMixedVariable() {
        doNotMatchTest("collect_on_mixed_variable")
    }

    fun testCollectOnIterableVariable() {
        doNotMatchTest("collect_on_iterable_variable")
    }

    fun testCollectOnUnionCollectionType() {
        doNotMatchTest("collect_on_union_collection_type")
    }

    fun testCollectionOnStdObject_property() {
        doNotMatchTest("collect_on_stdClass_property")
    }
}
