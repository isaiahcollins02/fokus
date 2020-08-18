package com.isaiahvonrundstedt.fokus.features.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.isaiahvonrundstedt.fokus.BuildConfig
import com.isaiahvonrundstedt.fokus.R
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseActivity
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BasePreference
import kotlinx.android.synthetic.main.layout_appbar.*

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setPersistentActionBar(toolbar)
        setToolbarTitle(R.string.activity_about)
    }

    companion object {
        const val ABOUT_REPOSITORY_URL = "https://github.com/reichsadmiral/fokus/issues/new"
        const val ABOUT_DEVELOPER_EMAIL = "isaiahcollins_02@live.com"

        class AboutFragment: BasePreference() {

            override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
                setPreferencesFromResource(R.xml.xml_about_main, rootKey)
            }

            override fun onStart() {
                super.onStart()

                findPreference<Preference>(R.string.key_notices)?.apply {
                    setOnPreferenceClickListener {
                        startActivity(Intent(context, NoticesActivity::class.java))
                        true
                    }
                }

                findPreference<Preference>(R.string.key_translate)?.apply {
                    setOnPreferenceClickListener {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("mailto:${ABOUT_DEVELOPER_EMAIL}")
                        }
                        startActivity(intent)
                        true
                    }
                }

                findPreference<Preference>(R.string.key_report_issue)?.apply {
                    setOnPreferenceClickListener {
                        val browserIntent = CustomTabsIntent.Builder().build()
                        browserIntent.launchUrl(requireContext(), Uri.parse(ABOUT_REPOSITORY_URL))

                        true
                    }
                }

                findPreference<Preference>(R.string.key_version)?.apply {
                    summary = BuildConfig.VERSION_NAME
                }
            }
        }
    }
}