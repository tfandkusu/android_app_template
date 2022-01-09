package com.tfandkusu.template.view.info

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.tfandkusu.template.info.view.R

class InfoFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_info, rootKey)
    }
}
