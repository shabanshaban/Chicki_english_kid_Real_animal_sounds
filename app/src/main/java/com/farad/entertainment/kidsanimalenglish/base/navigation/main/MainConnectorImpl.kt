package com.farad.entertainment.kidsanimalenglish.base.navigation.main


import com.farad.entertainment.kidsanimalenglish.ui.activity.main.MainActivity

interface MainConnectorImpl : MainConnector {

    private fun getMainDialogManager() = getMainActivity().dialogManager
    private fun getMainActivity() = baseActivity as MainActivity
    private fun getNavigationManager() = getMainActivity().navigationManager







    override fun restartDestination(isAllowPopup: Boolean) {
        getNavigationManager()?.restartDestination(isAllowPopup)
    }



}