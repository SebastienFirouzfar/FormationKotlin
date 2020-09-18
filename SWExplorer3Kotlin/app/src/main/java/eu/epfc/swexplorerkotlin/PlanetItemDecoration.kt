package eu.epfc.swexplorerkotlin

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PlanetItemDecoration(largePadding : Int, smallPadding : Int) : RecyclerView.ItemDecoration() {

    private val largePadding: Int = largePadding
    private val smallPadding: Int = smallPadding

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.left = largePadding
        outRect.right = largePadding
        outRect.top = smallPadding
        outRect.bottom = smallPadding
    }
}