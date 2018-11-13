package pt.nmplcpimenta.innowavegithuber.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.githubuser_list_content.view.*
import pt.nmplcpimenta.innowave_githuber.R
import pt.nmplcpimenta.innowavegithuber.GitHubUserDetailActivity
import pt.nmplcpimenta.innowavegithuber.GitHubUserDetailFragment
import pt.nmplcpimenta.innowavegithuber.GitHubUserListActivity
import pt.nmplcpimenta.innowavegithuber.businessmanagers.Consuela
import pt.nmplcpimenta.innowavegithuber.datamodels.GHSearchUser

class GHSearchUserRecyclerViewAdapter(private val values: List<GHSearchUser>,
                                      private val itemClickHander: ((GHSearchUser) -> Unit)?) :
            RecyclerView.Adapter<GHSearchUserRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val ghSearchUser = v.tag as GHSearchUser

                if (itemClickHander != null) itemClickHander.invoke(ghSearchUser)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.githubuser_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val ghSearchUser = values[position]
            Consuela.getImage(ghSearchUser.avatarUrl) { ghError, bitmap ->
                if (ghError == null) {
                    holder.avatarView.setImageBitmap(bitmap!!)
                } else {
                    // TODO Deal with error, prolly put a placeholder
                }
            }
            holder.contentView.text = ghSearchUser.login

            with(holder.itemView) {
                tag = ghSearchUser
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val avatarView: ImageView = view.avatar
            val contentView: TextView = view.content
        }
    }