import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appassignment.DiaryDisplay
import com.example.appassignment.DiaryEntry
import com.example.appassignment.DiaryNote

class PageAdapter(fa: FragmentActivity, private val mNumOfTabs: Int) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return mNumOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DiaryEntry.newInstance()
            1 -> DiaryNote.newInstance()
            2 -> DiaryDisplay.newInstance()
            else -> DiaryEntry.newInstance()
        }
    }
}
