package br.com.dio.myportifolio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import br.com.dio.myportifolio.R
import br.com.dio.myportifolio.core.createDialog
import br.com.dio.myportifolio.core.createProgressDialog
import br.com.dio.myportifolio.core.hideSoftKeyboard
import br.com.dio.myportifolio.databinding.ActivityMainBinding
import br.com.dio.myportifolio.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


// Referencia do Projeto
// https://github.com/nglauber/books_jetpack

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.rvRepo.adapter =  adapter

        viewModel.getRepoList("jhchiarelli")
        viewModel.repos.observe(this) {
            when (it) {
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
//        Log.e(TAG, "onQueryTextSubmit: $query")
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        return true
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextSubmit: $newText")
        return false
    }

    companion object {
        private const val TAG = "TAG"
    }
}