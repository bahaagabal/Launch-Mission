package com.challenge.selaunchmission.mocks

import com.challenge.selaunchmission.GetLaunchInfoQuery
import com.challenge.selaunchmission.GetLaunchesQuery
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchSnippet
import com.challenge.selaunchmission.domain.models.Mission
import com.challenge.selaunchmission.domain.models.Rocket

object MockModel {
    val firstLaunch: LaunchSnippet = LaunchSnippet(
        id = "110",
        missionName = "CRS-21",
        missionPatch = "https://imgur.com/E7fjUBD.png",
        rocketName = "Falcon 9"
    )

    val secondLaunch: LaunchSnippet = LaunchSnippet(
        id = "109",
        missionName = "Starlink-15 (v1.0)",
        missionPatch = "https://images2.imgbox.com/d2/3b/bQaWiil0_o.png",
        rocketName = "Falcon 9"
    )

    val thirdLaunch: LaunchSnippet = LaunchSnippet(
        id = "108",
        missionName = "Sentinel-6 Michael Freilich",
        missionPatch = "",
        rocketName = "Falcon 9"
    )

    val firstLaunchInfo: Launch = Launch(
        id = "110",
        site = "KSC LC 39A",
        mission = Mission(
            name = "CRS-21",
            missionPatch = "https://imgur.com/E7fjUBD.png"
        ),
        rocket = Rocket(
            id = "falcon9",
            name = "Falcon 9",
            type = "FT",
        ),
        isBooked = false,
    )

    val firstLaunchGetLaunches = GetLaunchesQuery.Launch(
        id = "110",
        mission = GetLaunchesQuery.Mission(
            name = "CRS-21",
            missionPatch = "https://imgur.com/E7fjUBD.png",
        ),
        rocket = GetLaunchesQuery.Rocket(
            name = "Falcon 9",
        ),
    )

    val firstGetLaunchInfo = GetLaunchInfoQuery.Launch(
        id = "110",
        mission = GetLaunchInfoQuery.Mission(
            name = "CRS-21",
            missionPatch = "https://imgur.com/E7fjUBD.png",
        ),
        rocket = GetLaunchInfoQuery.Rocket(
            name = "Falcon 9",
            id = "falcon9",
            type = "FT",
        ),
        site = "KSC LC 39A",
        isBooked = false,
    )
}
