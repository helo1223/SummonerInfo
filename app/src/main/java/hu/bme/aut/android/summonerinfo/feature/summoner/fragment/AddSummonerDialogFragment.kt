package hu.bme.aut.android.summonerinfo.feature.summoner.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import hu.bme.aut.android.summonerinfo.R
import hu.bme.aut.android.summonerinfo.databinding.DialogNewSummonerBinding

class AddSummonerDialogFragment : AppCompatDialogFragment() {

    private var _binding: DialogNewSummonerBinding? = null
    private val binding get() = _binding!!

    private lateinit var listener: AddSummonerDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = if (targetFragment != null) {
                targetFragment as AddSummonerDialogListener
            } else {
                activity as AddSummonerDialogListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogNewSummonerBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_summoner)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { dialogInterface, i ->
                listener.onSummonerAdded(binding.newSummonerDialogEditText.text.toString())
            }
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    private fun getContentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.dialog_new_summoner, null)
    }

    interface AddSummonerDialogListener {
        fun onSummonerAdded(summoner: String?)
    }
}