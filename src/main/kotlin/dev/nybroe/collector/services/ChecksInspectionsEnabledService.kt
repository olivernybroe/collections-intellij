package dev.nybroe.collector.services

import com.intellij.codeInsight.daemon.HighlightDisplayKey
import com.intellij.codeInspection.InspectionProfile
import com.intellij.codeInspection.ex.InspectionProfileImpl
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project
import com.intellij.profile.codeInspection.InspectionProfileManager
import dev.nybroe.collector.inspections.ArrayMapToCollectionInspection
import dev.nybroe.collector.inspections.ClosureToArrowFunctionInspection
import dev.nybroe.collector.inspections.CollectFunctionInCollectionInspection
import dev.nybroe.collector.inspections.ForeachToCollectionInspection
import dev.nybroe.collector.inspections.MapFlattenToFlatMapInspection

class ChecksInspectionsEnabledService(val project: Project) {
    private val inspections = listOf(
        ArrayMapToCollectionInspection(),
        ClosureToArrowFunctionInspection(),
        CollectFunctionInCollectionInspection(),
        ForeachToCollectionInspection(),
        MapFlattenToFlatMapInspection()
    )

    init {
        val profile = InspectionProfileManager.getInstance(project).currentProfile

        if (!isCollectionInspectionsEnabled(profile)) {
            NotificationGroupManager.getInstance().getNotificationGroup("Collector")
                .createNotification(
                    "Inspections for collector is disabled.",
                    "The inspections can be enabled in preferences or by pressing the button.",
                    NotificationType.INFORMATION
                ).addAction(
                    NotificationAction.createSimple(
                        "Enable inspections"
                    ) { enableCollectionInspections(profile) }
                ).notify(project)
        }
    }

    private fun isCollectionInspectionsEnabled(profile: InspectionProfile): Boolean {
        return inspections.all { profile.isToolEnabled(HighlightDisplayKey.findById(it.id)) }
    }

    private fun enableCollectionInspections(profile: InspectionProfileImpl) {
        return inspections.forEach { profile.enableTool(it.shortName, project) }
    }
}
