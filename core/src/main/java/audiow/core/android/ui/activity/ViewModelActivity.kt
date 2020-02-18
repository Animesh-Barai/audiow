package audiow.core.android.ui.activity

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import audiow.core.android.viewmodel.ViewModelFactory
import audiow.core.util.standart.getTypeParameterClass
import javax.inject.Inject

abstract class ViewModelActivity<VM : ViewModel> : BaseActivity() {
    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory<VM>

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getTypeParameterClass())
    }
}