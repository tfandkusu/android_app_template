package com.tfandkusu.template.view.info

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.tfandkusu.template.info.view.R

class InfoFragment : PreferenceFragmentCompat() {
    companion object {
        private const val KEY_OSS = "oss"

        private const val KEY_ABOUT = "about"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_info, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>(KEY_OSS)?.setOnPreferenceClickListener {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.title_oss_license))
            val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
            startActivity(intent)
            true
        }
    }
}
