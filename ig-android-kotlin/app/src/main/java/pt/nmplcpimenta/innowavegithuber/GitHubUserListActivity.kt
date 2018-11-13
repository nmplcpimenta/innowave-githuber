package pt.nmplcpimenta.innowavegithuber

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_githubuser_list.*
import kotlinx.android.synthetic.main.githubuser_list.*
import pt.nmplcpimenta.innowave_githuber.R
import pt.nmplcpimenta.innowavegithuber.adapters.GHSearchUserRecyclerViewAdapter
import pt.nmplcpimenta.innowavegithuber.businessmanagers.Consuela
import pt.nmplcpimenta.innowavegithuber.datamodels.GHSearchUser

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [GitHubUserDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class GitHubUserListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private var ghSearchUsersResults: List<GHSearchUser> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_githubuser_list)

        //setSupportActionBar(toolbar)
        //toolbar.title = title

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        if (githubuser_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(githubuser_list)
    }

    override fun onResume() {
        super.onResume()

        Consuela.searchUsers("nmpl") { ghError, searchResults ->
            if (searchResults != null) {
                ghSearchUsersResults = searchResults

                setupRecyclerView(githubuser_list)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = GHSearchUserRecyclerViewAdapter(ghSearchUsersResults) { ghSearchUser ->
            Consuela.getUser(ghSearchUser.login) { ghError, ghUser ->
                if (ghError != null) {
                    // TODO Deal with error
                } else {
                    if (twoPane) {
                        val fragment = GitHubUserDetailFragment().apply {
                            arguments = Bundle().apply {
                                putSerializable(GitHubUserDetailFragment.ARG_ITEM_ID, ghUser)
                                putBoolean(GitHubUserDetailFragment.ARG_TWO_PANE, twoPane)
                            }
                        }

                        supportFragmentManager
                                .beginTransaction()
                                .replace(R.id.githubuser_detail_container, fragment)
                                .commit()
                    } else {
                        val intent = Intent(this, GitHubUserDetailActivity::class.java).apply {
                            putExtra(GitHubUserDetailFragment.ARG_ITEM_ID, ghUser)
                            putExtra(GitHubUserDetailFragment.ARG_TWO_PANE, twoPane)
                        }

                        startActivity(intent)
                    }
                }
            }
        }
    }
}
