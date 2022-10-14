package com.sultanseidov.watchyou.data.entities

import android.os.Parcelable
import es.anthorlop.stories.datatype.Avatar
import es.anthorlop.stories.datatype.Story
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UpcomingMovieStoryModel(
    var storyList: ArrayList<Story>?=null,
    var avatarList:ArrayList<Avatar>?=null
) :
    Parcelable
