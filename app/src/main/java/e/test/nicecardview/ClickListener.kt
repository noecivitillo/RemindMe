package e.test.nicecardview


import e.test.nicecardview.model.Note



/**
 * Created by Noe on 5/3/2018.
 */
interface ClickListener {
    fun onDeleteClicked(position: Int)
    fun onSaveClicked(text: String, position: Int)
    fun onShareClicked(position: Int)
    fun onPriorityClicked(position: Int)
    fun onReminderClicked(position: Int)
    fun onReminderEdited(position: Int)
}