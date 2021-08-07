package mj.project.delievery.screen.main.my

import mj.project.delievery.databinding.FragmentMyBinding
import mj.project.delievery.screen.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MyFragment: BaseFragment<MyViewModel, FragmentMyBinding>() {
    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun observeData() {

    }

    companion object {
        const val TAG = "MyFragment"
        fun newInstance() = MyFragment()
    }

}