package com.smartkids.learning.domain.usecase

import com.smartkids.learning.domain.model.LearningProgress
import com.smartkids.learning.domain.repository.LearningRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetWeakTopicsUseCase @Inject constructor(
    private val repository: LearningRepository
) {
    operator fun invoke(): Flow<List<LearningProgress>> {
        return repository.getWeakTopics().map { list ->
            list.filter { it.totalAttempts > 0 }.take(5)
        }
    }
}