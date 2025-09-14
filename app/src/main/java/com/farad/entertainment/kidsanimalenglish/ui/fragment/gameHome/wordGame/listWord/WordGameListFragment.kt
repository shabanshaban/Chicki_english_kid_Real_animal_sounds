package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.listWord

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGameWordListBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.vm.WordGameViewModel
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.shareText
import com.farad.entertainment.kidsanimalenglish.utils.toast
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class WordGameListFragment : BottomNavigationFragment<FragmentGameWordListBinding>() {


    private val wordGameListAdapter = WordGameListAdapter()
    private val wordActivityViewModel by activityViewModel<WordGameViewModel>()


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameWordListBinding
        get() = FragmentGameWordListBinding::inflate

    override fun setup() {

        initRecyclerview()
        listener()
    }


    override fun initObserveViewModel() {

        wordActivityViewModel.getAllWordGameLiveData().observe(this) {
            it?.let {
                it.filter { f -> f.isOpen }.also { filter ->
                    wordActivityViewModel.listSortOpenWord.clear()
                    wordActivityViewModel.listSortOpenWord.addAll(filter.sortedBy { it.idItem })
                }
                it.filter { f -> !f.isOpen }.also { filter ->
                    wordActivityViewModel.listSortOpenWord.addAll(filter)
                }
                wordGameListAdapter.submitList(wordActivityViewModel.listSortOpenWord)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        changeScreenOrientation(false)
    }

    private fun listener() {
        binding.btnShare.setOnSafeClickListener {
            context?.invitedFriend()
        }
    }

    private fun initRecyclerview() {

        binding.recyclerview.adapter = wordGameListAdapter.apply {
            setOnItemClickListener { word, position ->
                if (word.isOpen) {
                    val navigate =
                        WordGameListFragmentDirections.actionWordGameListFragmentToWordGameFragment(
                            position,
                            word
                        )
                    navigate(navigate)
                } else {
                    toast(getString(R.string.lock_it_my_dear))
                }
            }
        }
    }


}