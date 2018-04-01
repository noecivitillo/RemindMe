package e.test.nicecardview

import android.content.Context
import android.graphics.Color
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import e.test.nicecardview.model.Note
import e.test.nicecardview.ui.NotePresenter
import kotlinx.android.synthetic.main.recycler_container.*


/**
 * Created by Noe on 21/2/2018.
 */

import kotlinx.android.synthetic.main.recycler_container.view.*



class AdapterNotes(val notes: List<Note>, val listener: (Note) -> Boolean, var clickListener: ClickListener)  : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.recycler_container))
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var txt= notes[position].content
        holder.bind(notes[position], listener)

        val note = notes.get(position)

        //check
        if(note.important){
            //holder.itemView.btn_priority_toolbar.visibility= View.VISIBLE
        }
        if(!note.reminder.equals("")){
            holder.itemView.reminder_text.visibility= View.VISIBLE
        }

        Log.i("AdapterNotes", " NOTE " + note.id + " CONTENT " +note.content)
        //holder.itemView.edit_text.setText(notes[position].content)
        holder.itemView.edit_text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    txt = p0.toString()
                    //Log.i(" AFTER TEXT changed ", " Text " +p0.toString())
        }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Log.i(" BEFORE TEXT changed ", " Text " +p0.toString())

        }       override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                   // Log.i(" ON TEXT changed ", " Text " +p0.toString())
                    //notes[position].content = p0.toString()
                    txt = p0.toString()

        }})
        holder.itemView.btn_delete.setOnClickListener{
            clickListener.onDeleteClicked(position)
            Log.i("OnBindViewHolder ", "DELETE clicked "+position)
        }
        holder.itemView.btn_share.setOnClickListener{
            clickListener.onShareClicked(position)
        }
        holder.itemView.btn_important.setOnClickListener{
            clickListener.onPriorityClicked(position)
           // holder.itemView.btn_priority_toolbar.visibility=View.VISIBLE
        }
        holder.itemView.btn_reminder.setOnClickListener{
            clickListener.onReminderClicked(position)
        }
        holder.itemView.reminder_text.setOnClickListener{
            clickListener.onReminderEdited(position)
        }
        holder.itemView.btn_done.setOnClickListener{
            clickListener.onSaveClicked(txt, position)
            note.content= txt
            holder.itemView.edit_text.isCursorVisible=false
            Log.i("OnBindViewHolder ", "SAVE clicked "+ position + " TEXT SAVED = " +note.content + " NOTE id = " + note.id)
        }
    }
    override fun getItemCount(): Int = notes.size

}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    //val delete_btn = itemView.btn_delete
    /**
    override fun onDeleteClicked(note: Note) {
        Log.i("ViewHolder ", "Position clicked "+position)
    }
    */
    fun bind(note: Note, listener: (Note) -> Boolean) = with(itemView) {
            edit_text.setText(note.content)
            image.setImageResource(note.color)
            edit_text.setBackgroundResource(Color.TRANSPARENT)
            reminder_text.setText(note.reminder)
            setOnLongClickListener {
                listener(note)
                edit_text.isCursorVisible = false
                layout_buttons.visibility= View.VISIBLE
                true
            }
            setOnClickListener { listener (note)
                edit_text.isCursorVisible = true
                layout_buttons.visibility= View.GONE
            }
        /**
            btn_done.setOnClickListener{
                edit_text.isCursorVisible = false
                btn_done.setBackgroundResource(R.drawable.ic_create_black_24dp)
            }
            */
            btn_delete.setOnClickListener{
                btn_delete.setOnClickListener {
                    //onDeleteClicked(adapterPosition)
                    Log.i("Adapter - ViewHolder ", "Position clicked "+adapterPosition)
                    //Toast.makeText(btn_delete.context, "Deleted", Toast.LENGTH_SHORT).show()
                }
            }

    }

}
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}
