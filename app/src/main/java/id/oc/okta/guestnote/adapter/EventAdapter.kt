package id.oc.okta.guestnote.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import id.oc.okta.guestnote.fitur.NoteActivity
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.database.EventModel
import kotlinx.android.synthetic.main.item_event.view.*


class EventAdapter(val items: MutableList<EventModel>, val context: Context) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_event, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvKategoriName.text = items[position].nama
        Glide.with(context).load(stringToBitMap(items[position].image)).into(viewHolder.tvKategoriImage)
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra("Data", items[position].nama)
            intent.putExtra("IdEvent", items[position].id.toString())
            context.startActivity(intent)
        }
    }

    private fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvKategoriName: TextView = view.TVEvent
        val tvKategoriImage: ImageView = view.IVEvent
    }
}