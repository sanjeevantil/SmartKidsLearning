package com.smartkids.learning.domain.model

data class Achievement(
    val achievementId: String,
    val title: String,
    val description: String,
    val iconName: String,
    val category: String,
    val requirementType: String,
    val requirementValue: Int,
    val currentValue: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedTimestamp: Long? = null,
    val xpReward: Int = 0,
    val coinReward: Int = 0
) {
    val progressPercentage: Float
        get() = if (requirementValue > 0) {
            (currentValue.toFloat() / requirementValue).coerceIn(0f, 1f)
        } else 0f
}