package e.test.nicecardview

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.Spinner

/**
 * Created by Noe on 28/3/2018.
 */

class CustomSpinner : Spinner {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, mode: Int) : super(context, mode)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) : super(context, attrs, defStyleAttr, mode)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int, mode: Int) : super(context, attrs, defStyleAttr, defStyleRes, mode)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int, mode: Int, popupTheme: Resources.Theme?) : super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme)


    override fun setSelection(position: Int, animate: Boolean) {
        val sameSelected : Boolean = position == selectedItemPosition
        super.setSelection(position, animate)

        if(sameSelected){
            onItemSelectedListener.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }
    override fun setSelection(position: Int) {
        val sameSelected : Boolean = position == selectedItemPosition
        super.setSelection(position)

        if(sameSelected){
            onItemSelectedListener.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }
}