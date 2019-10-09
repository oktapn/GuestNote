package id.oc.okta.guestnote.adapter

import android.content.Context
import android.graphics.Typeface
import androidx.recyclerview.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.database.EventModel
import id.oc.okta.guestnote.database.NoteModel
import id.oc.okta.guestnote.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(val items: MutableList<NoteModel>, val context: Context) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.tvUserName?.text = items[p1].nama
        p0.tvUserComment?.text = items[p1].note
        val typeface = Typeface.createFromAsset(context.assets, "icomoon.ttf")
        p0.ivUserImage.typeface = typeface
        p0.ivUserImage.text = Html.fromHtml("&#xe900;")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUserName: TextView = view.TVNamaNote
        val tvUserComment: TextView = view.TVIsiNote
        val ivUserImage: TextView = view.IVNote
    }
}