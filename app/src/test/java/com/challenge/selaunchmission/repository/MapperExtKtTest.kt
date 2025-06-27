package com.challenge.selaunchmission.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.challenge.selaunchmission.GetLaunchInfoQuery
import com.challenge.selaunchmission.GetLaunchesQuery
import com.challenge.selaunchmission.domain.models.Launch
import com.challenge.selaunchmission.domain.models.LaunchSnippet
import com.challenge.selaunchmission.domain.models.Mission
import com.challenge.selaunchmission.domain.models.Rocket
import com.challenge.selaunchmission.mocks.MockModel
import org.junit.Test

class MapperExtKtTest {

    @Test
    fun `map a launch from the get launches query should map to the domain model properly`() {
        val launch = MockModel.firstLaunchGetLaunches

        assertThat(launch.toLaunchSnippet()).isEqualTo(MockModel.firstLaunch)
    }

    @Test
    fun `map a launch from the get launches query should map to the domain model properly when some properties are null`() {
        val launch = GetLaunchesQuery.Launch(
            id = "110",
            mission = null,
            rocket = null,
        )

        assertThat(launch.toLaunchSnippet()).isEqualTo(
            LaunchSnippet(
                id = "110",
                missionName = "",
                missionPatch = "",
                rocketName = "",
            )
        )
    }

    @Test
    fun `map a launch from the get launch info query should map to the domain model properly`() {
        val launch = MockModel.firstGetLaunchInfo

        assertThat(launch.toLaunch()).isEqualTo(MockModel.firstLaunchInfo)
    }

    @Test
    fun `map a launch from the get launch info query should map to the domain model properly when some properties are null`() {
        val launch = GetLaunchInfoQuery.Launch(
            id = "110",
            mission = null,
            rocket = null,
            site = null,
            isBooked = false,
        )

        assertThat(launch.toLaunch()).isEqualTo(
            Launch(
                id = "110",
                site = "",
                mission = Mission(
                    name = "",
                    missionPatch = ""
                ),
                rocket = Rocket(
                    id = "",
                    name = "",
                    type = "",
                ),
                isBooked = false,
            )
        )
    }
}
