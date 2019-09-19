package ismaeldivita.audioma.core.android.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import ismaeldivita.audioma.core.android.viewmodel.ViewModelFactory
import ismaeldivita.audioma.core.util.standart.getTypeParameterClass
import javax.inject.Inject

abstract class ViewModelFragment<VM : ViewModel> : BaseFragment() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory<VM>

    protected lateinit var viewModel: VM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getTypeParameterClass())
    }

}
