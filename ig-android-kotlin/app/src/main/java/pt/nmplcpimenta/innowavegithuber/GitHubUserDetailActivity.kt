package pt.nmplcpimenta.innowavegithuber

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_githubuser_detail.*
import pt.nmplcpimenta.innowave_githuber.R
import pt.nmplcpimenta.innowavegithuber.GitHubUserDetailFragment.Companion.ARG_ITEM_ID
import pt.nmplcpimenta.innowavegithuber.GitHubUserDetailFragment.Companion.ARG_TWO_PANE
import pt.nmplcpimenta.innowavegithuber.auxiliary.AppBarStateChangeListener
import pt.nmplcpimenta.innowavegithuber.datamodels.GHUser

/**
 * An activity representing a single GitHubUser detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [GitHubUserListActivity].
 */
class GitHubUserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_githubuser_detail)
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val ghUser = intent.getSerializableExtra(ARG_ITEM_ID) as GHUser

        if (ghUser.name.isEmpty() || ghUser.name.isBlank() || ghUser.name.equals("null")) {
            supportActionBar?.title = "@${ghUser.login}"
            toolbar_subtitle?.visibility = View.GONE
        } else {
            supportActionBar?.title = ghUser.name
            supportActionBar?.subtitle = "@${ghUser.login}"
            toolbar_subtitle?.visibility = View.VISIBLE
            toolbar_subtitle?.text = "@${ghUser.login}"
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val fragment = GitHubUserDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ITEM_ID, intent.getSerializableExtra(ARG_ITEM_ID))
                    putBoolean(ARG_TWO_PANE, intent.getBooleanExtra(ARG_TWO_PANE, false))
                }
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.githubuser_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    // This ID represents the Home or Up button. In the case of this
                    // activity, the Up button is shown. Use NavUtils to allow users
                    // to navigate up one level in the application structure. For
                    // more details, see the Navigation pattern on Android Design:
                    //
                    // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                    NavUtils.navigateUpTo(this, Intent(this, GitHubUserListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
