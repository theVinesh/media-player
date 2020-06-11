package com.thevinesh.mediaplayer.repository

import kotlinx.coroutines.flow.Flow

interface IMediaRepository {
    suspend fun getAllVideos(): Flow<MediaRepository.State>
}
