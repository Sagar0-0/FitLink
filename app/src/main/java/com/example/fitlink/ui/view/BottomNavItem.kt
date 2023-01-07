package com.example.fitlink.ui.view

import com.example.fitlink.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_baseline_home_24,"home")
    object Search: BottomNavItem("Search",R.drawable.ic_baseline_search_24,"search")
    object BookMarks: BottomNavItem("Saved",R.drawable.ic_outline_bookmarks_24,"bookmarks")
    object Profile: BottomNavItem("Profile",R.drawable.ic_baseline_account_circle_24,"profile")
}