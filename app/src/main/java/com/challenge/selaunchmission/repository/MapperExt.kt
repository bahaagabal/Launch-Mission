package com.challenge.selaunchmission.repository

import com.challenge.selaunchmission.GetLaunchInfoQuery
import com.challenge.selaunchmission.GetLaunchesQuery
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchSnippet
import com.challenge.selaunchmission.domain.models.Mission
import com.challenge.selaunchmission.domain.models.Rocket

fun GetLaunchesQuery.Launch.toLaunchSnippet(): LaunchSnippet =
    LaunchSnippet(
        id = id,
        missionName = mission?.name.orEmpty(),
        missionPatch = mission?.missionPatch.orEmpty(),
        rocketName = rocket?.name.orEmpty(),
    )

fun GetLaunchInfoQuery.Launch.toLaunch(): Launch =
    Launch(
        id = id,
        site = site.orEmpty(),
        mission = Mission(
            name = mission?.name.orEmpty(),
            missionPatch = mission?.missionPatch.orEmpty(),
        ),
        rocket = Rocket(
            id = rocket?.id.orEmpty(),
            name = rocket?.name.orEmpty(),
            type = rocket?.type.orEmpty(),
        ),
        isBooked = isBooked,
    )
