package e.test.nicecardview


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import dagger.android.AndroidInjection
import e.test.nicecardview.model.Note
import e.test.nicecardview.ui.NotePresentation
import e.test.nicecardview.ui.NotePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycler_container.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() , NotePresentation, ClickListener, ReminderDialogFragment.OnSavedReminderListener {

    @Inject lateinit var presenter: NotePresenter
    var listNotes = ArrayList<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        val layoutManager = StaggeredGridLayoutManager(2, 1)
            layoutManager.gapStrategy =  StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            recycler.layoutManager= layoutManager
       // recycler.itemAnimator= DefaultItemAnimator()

        recycler.adapter= AdapterNotes(presenter.notes, {true}, this)

        presenter.onCreate(this)


    }
    override fun onSavedReminder(id: Long, date: String) {
        Log.i("MainActivity" , "Saved Reminder id " + id + " date " +date)
        val note: Note = presenter.getNoteById(id)
        if(date!=("")) {
            note.reminder = date
            presenter.update(note)
            presenter.loadNotes()
        }else{
            note.reminder = ""
            presenter.update(note)
            presenter.loadNotes()
        }

    }
    override fun onDeleteClicked(position: Int) {
        Log.i("MainActivity" , "Note clicked "+position)
        val note : Note = presenter.notes[position]

        val alarmIntent = Intent(applicationContext, AlarmReceiver::class.java)

        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        //testing unique request code
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, note.id.toInt(),  alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager?.cancel(pendingIntent)


        presenter.delete(presenter.notes.get(position))
        recycler.adapter.notifyItemRemoved(position)
        toast("Deleted")

    }
    override fun onShareClicked(position: Int) {

    }
    override fun onPriorityClicked(position: Int) {
        val note : Note = presenter.notes[position]
        note.important = true
        presenter.notes.removeAt(position)
        presenter.notes.add(0, note)
        presenter.update(note)
        recycler.adapter.notifyItemChanged(position)
    }
    override fun onReminderClicked(position: Int) {
        initReminderFragment(position)

    }
    override fun onSaveClicked(text: String, position: Int) {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
            val note : Note = presenter.notes[position]
            note.content= text
            presenter.update(note)
            recycler.adapter.notifyItemChanged(position)
            Log.i("MainActivity" , "Note SAVED POSITION "+ position + " Text " + note.content + "Note id " + note.id)

        }

        toast("Saved")

    }
    override fun onReminderEdited(position: Int) {
        initReminderFragment(position)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                 presenter.addNewNote(pickColor())
                //test
                 presenter.loadNotes()
                //-- edittext null--
                //edit_text.isCursorVisible= true
                return true
            }
            else-> return super.onOptionsItemSelected(item)
        }

    }
    override fun showNotes(notes: List<Note>) {
        recycler?.adapter = AdapterNotes(notes,{
            true }, this)
    }

    override fun noteAddedAt(position: Int) {
        recycler?.adapter?.notifyItemInserted(position)
    }
    override fun scrollTo(position: Int) {
        recycler?.smoothScrollToPosition(position)
    }
    private fun initReminderFragment(position: Int){
        val note: Note = presenter.notes[position]

        val bundle = Bundle()
        bundle.putString("title", note.content)
        bundle.putLong("id", note.id)

        val reminderFragment = ReminderDialogFragment()
        reminderFragment.arguments= bundle
        reminderFragment.show(supportFragmentManager, "ReminderDialogFragment")
    }
    private fun pickColor(): Int{
        val colors =
                intArrayOf(R.color.light_blue_500,
                        R.color.deep_purple_200,
                        R.color.pink_200,
                        R.color.light_blue_500,
                        R.color.red_a100,
                        R.color.purple_a100,
                        R.color.teal_a700,
                        R.color.deep_purple_200,
                        R.color.teal_a700,
                        R.color.light_blue_500,
                        R.color.deep_purple_200,
                        R.color.pink_200,
                        R.color.purple_200,
                        R.color.light_blue_500,
                        R.color.red_a100,
                        R.color.purple_a100,
                        R.color.teal_a700,
                        R.color.deep_purple_200)

        val color = Random().nextInt(colors.size)
        return colors.get(color)

    }


}
