package com.myniprojects.compass

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myniprojects.compass.ui.composes.destination.DestinationSelector
import com.myniprojects.compass.ui.theme.CompassTheme
import com.myniprojects.compass.utils.makeLocation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class DestinationTesting
{
    private val testLocation = makeLocation(1.1, 2.2)
    
    private lateinit var destinationNotSelected: String
    private lateinit var pickOne: String
    private lateinit var destination: String
    private lateinit var giveLocationPermission: String
    private lateinit var turnGpsToSeeDist: String
    private lateinit var distance: String
    private lateinit var cordFormat: String
    private lateinit var metersFormat: String
    private lateinit var kmFormat: String
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Composable
    fun SetupStrings()
    {
        destinationNotSelected = stringResource(id = R.string.destination_isnt_selected)
        pickOne = stringResource(id = R.string.pick_one)
        destination = stringResource(id = R.string.destination)
        giveLocationPermission = stringResource(id = R.string.give_location_permission)
        turnGpsToSeeDist = stringResource(id = R.string.turn_on_gps_to_see_distance)
        distance = stringResource(id = R.string.distance)
        cordFormat = stringResource(id = R.string.cord_format)
        metersFormat = stringResource(id = R.string.distance_format_m)
        kmFormat = stringResource(id = R.string.distance_format_km)
    }
    
    
    @Test
    fun no_destination()
    {
        var wasDestinationDialogClicked = false
        composeTestRule.setContent {
            CompassTheme {
                DestinationSelector(
                    setDestinationOnClick = {
                        wasDestinationDialogClicked = true
                    },
                    destination = null,
                    distance = null,
                    locationPermission = false,
                    isGpsTurnOn = false,
                    requestLocationPermission = {}
                )
                
                SetupStrings()
            }
        }
        
        // text with info exists
        composeTestRule.onNodeWithText(destinationNotSelected).assertExists()
        
        // button with picking destination exists
        val button = composeTestRule.onNodeWithText(pickOne)
        button.assertExists()
        
        // click of button handled
        button.performClick()
        assert(wasDestinationDialogClicked)
    }
    
    
    @Test
    fun with_destination_no_perms()
    {
        var wasLocationPermsRequested = false
        composeTestRule.setContent {
            CompassTheme {
                DestinationSelector(
                    setDestinationOnClick = { },
                    destination = testLocation,
                    distance = null,
                    locationPermission = false,
                    isGpsTurnOn = false,
                    requestLocationPermission = {
                        wasLocationPermsRequested = true
                    }
                )
                
                SetupStrings()
            }
        }
        
        
        // text with destination exists
        composeTestRule.onNodeWithText(destination).assertExists()
        
        // text with cords displayed correctly
        composeTestRule.onNodeWithText(
            cordFormat.format(
                testLocation.latitude,
                testLocation.longitude
            )
        ).assertExists()
        
        // button with info about location permission exists
        val button = composeTestRule.onNodeWithText(giveLocationPermission)
        button.assertExists()
        button.performClick()
        assert(wasLocationPermsRequested)
    }
    
    @Test
    fun with_destination_no_gps()
    {
        composeTestRule.setContent {
            CompassTheme {
                DestinationSelector(
                    setDestinationOnClick = { },
                    destination = testLocation,
                    distance = null,
                    locationPermission = true,
                    isGpsTurnOn = false,
                    requestLocationPermission = {}
                )
                
                SetupStrings()
            }
        }
        
        
        // text with destination exists
        composeTestRule.onNodeWithText(destination).assertExists()
        
        // text with cords displayed correctly
        composeTestRule.onNodeWithText(
            cordFormat.format(
                testLocation.latitude,
                testLocation.longitude
            )
        ).assertExists()
        
        // gps off text exists
        composeTestRule.onNodeWithText(turnGpsToSeeDist).assertExists()
        
    }
    
    @Test
    fun with_destination_success_meters()
    {
        val dist = 1234.5f
        composeTestRule.setContent {
            CompassTheme {
                DestinationSelector(
                    setDestinationOnClick = { },
                    destination = testLocation,
                    distance = dist,
                    locationPermission = true,
                    isGpsTurnOn = true,
                    requestLocationPermission = {}
                )
                
                SetupStrings()
            }
        }
        
        
        // text with destination exists
        composeTestRule.onNodeWithText(destination).assertExists()
        
        // text with cords displayed correctly
        composeTestRule.onNodeWithText(
            cordFormat.format(
                testLocation.latitude,
                testLocation.longitude
            )
        ).assertExists()
        
        // distance text exists
        composeTestRule.onNodeWithText(distance).assertExists()
        
        // distance in meters is shown
        composeTestRule.onNodeWithText(metersFormat.format(dist)).assertExists()
    }
    
    @Test
    fun with_destination_success_kilometers()
    {
        val dist = 123456.7f
        composeTestRule.setContent {
            CompassTheme {
                DestinationSelector(
                    setDestinationOnClick = { },
                    destination = testLocation,
                    distance = dist,
                    locationPermission = true,
                    isGpsTurnOn = true,
                    requestLocationPermission = {}
                )
                
                SetupStrings()
            }
        }
        
        // text with destination exists
        composeTestRule.onNodeWithText(destination).assertExists()
        
        // text with cords displayed correctly
        composeTestRule.onNodeWithText(
            cordFormat.format(
                testLocation.latitude,
                testLocation.longitude
            )
        ).assertExists()
        
        // distance text exists
        composeTestRule.onNodeWithText(distance).assertExists()
        
        // distance in meters is shown
        composeTestRule.onNodeWithText(kmFormat.format(dist / 1000)).assertExists()
    }
}