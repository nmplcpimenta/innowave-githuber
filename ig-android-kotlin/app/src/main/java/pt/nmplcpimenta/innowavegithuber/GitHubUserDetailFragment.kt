package pt.nmplcpimenta.innowavegithuber

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ghuser_detail.*
import kotlinx.android.synthetic.main.githubuser_list.*
import pt.nmplcpimenta.innowave_githuber.R
import pt.nmplcpimenta.innowavegithuber.adapters.GHSearchUserRecyclerViewAdapter
import pt.nmplcpimenta.innowavegithuber.businessmanagers.Consuela
import pt.nmplcpimenta.innowavegithuber.datamodels.GHSearchUser
import pt.nmplcpimenta.innowavegithuber.datamodels.GHUser

/**
 * A fragment representing a single GitHubUser detail screen.
 * This fragment is either contained in a [GitHubUserListActivity]
 * in two-pane mode (on tablets) or a [GitHubUserDetailActivity]
 * on handsets.
 */
class GitHubUserDetailFragment : Fragment() {

    private var twoPane: Boolean = false
    private var ghUser: GHUser? = null
    private var followers = listOf<GHSearchUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                ghUser = it.getSerializable(ARG_ITEM_ID) as GHUser
            }

            if (it.containsKey(ARG_TWO_PANE)) {
                twoPane = it.getBoolean(ARG_TWO_PANE)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_ghuser_detail, container, false)

        return rootView
    }

    override fun onResume() {
        super.onResume()

        updateUI()

        if (ghUser != null) {
            Consuela.getFollowers(ghUser!!.login) { ghError, followers ->
                if (ghError != null) {
                    // TODO Deal with error
                } else {
                    this.followers = followers!!

                    refreshFollowers()
                }
            }
        }
    }

    private fun updateUI() {
        //nameLabel?.text = ghUser?.name

        println("TWO PANE = $twoPane")

        if (twoPane) {
            twopane_userdetail_container.visibility = View.VISIBLE
        } else {
            twopane_userdetail_container?.visibility = View.GONE
        }
    }

    private fun refreshFollowers() {
        if (this.followers.isEmpty()) {
            empty_view.visibility = View.VISIBLE
            followers_list.visibility = View.GONE
        } else {
            followers_list.adapter = GHSearchUserRecyclerViewAdapter(this.followers, null)

            empty_view.visibility = View.GONE
            followers_list.visibility = View.VISIBLE
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
        const val ARG_TWO_PANE = "two_pane"
    }
}
