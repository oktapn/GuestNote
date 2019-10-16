package id.oc.okta.guestnote.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.oc.okta.guestnote.fitur.EventActivity
import id.oc.okta.guestnote.R
import id.oc.okta.guestnote.model.Kategori
import kotlinx.android.synthetic.main.item_main.view.*

class KategoriAdapater(val items: ArrayList<Kategori>, val context: Context) : RecyclerView.Adapter<KategoriAdapater.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): KategoriAdapater.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: KategoriAdapater.ViewHolder, position: Int) {
        viewHolder.tvKategoriName?.text = items[position].nama
        Glide.with(context).load(items[position].image).into(viewHolder.tvKategoriImage)
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, EventActivity::class.java)
            intent.putExtra("Data", items[position].nama)
            context.startActivity(intent)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvKategoriName = view.TVMainItem
        val tvKategoriImage = view.IVMainItem
    }
}