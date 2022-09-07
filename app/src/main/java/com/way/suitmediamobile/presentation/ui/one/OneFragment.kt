package com.way.suitmediamobile.presentation.ui.one

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.way.suitmediamobile.data.local.model.PersonEntity
import com.way.suitmediamobile.databinding.FragmentOneBinding
import com.way.suitmediamobile.presentation.ui.MainActivity
import com.way.suitmediamobile.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class OneFragment : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mProfileUri: Uri
    private lateinit var mProfileBitmap: Bitmap
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    mProfileUri = fileUri
                    val imageSource =
                        ImageDecoder.createSource(activity!!.contentResolver, mProfileUri)
                    mProfileBitmap = ImageDecoder.decodeBitmap(imageSource)
                    binding.ivPicture.setImageURI(fileUri)
                }
                RESULT_ERROR -> {
                    Toast.makeText(context, "Error $data", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private var databaseIsNotEmpty: Boolean = false
    private var isPictureAvailable: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = (activity as MainActivity).mainViewModel

        checkIfUserNotEmpty()

        binding.ivPicture.setOnClickListener { selectImageFromGallery() }

        checkPalindrome()
        nextAndValidate()
    }

    private fun checkIfUserNotEmpty() {
        mainViewModel.readPersonEntity.observe(viewLifecycleOwner) { person ->
            if (!person.isNullOrEmpty()) {
                databaseIsNotEmpty = true
                isPictureAvailable = true
                binding.textFieldName.editText?.setText(person[0].name)
                binding.ivPicture.setImageBitmap(person[0].picture)
            }
        }
    }

    private fun nextAndValidate() {
        binding.btnNext.setOnClickListener {
            if (mainViewModel.validateInput(textName = binding.textFieldName.editText?.text.toString()) && isPictureAvailable) {
                lifecycleScope.launch {
                    val personName = binding.textFieldName.editText?.text.toString()
                    if (!databaseIsNotEmpty) {
                        saveProfile(personName)
                    }
                    val action = OneFragmentDirections.actionOneFragmentToTwoFragment(personName)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun checkPalindrome() {
        binding.btnCheck.setOnClickListener {
            if (mainViewModel.validateInput(palindromeText = binding.textFieldPalindrome.editText?.text.toString()))
                mainViewModel.isPalindrome(binding.textFieldPalindrome.editText?.text.toString())
        }
    }

    private fun saveProfile(name: String) {
        val personEntity = PersonEntity(name, mProfileBitmap)
        mainViewModel.insertPerson(personEntity)
    }

    private fun selectImageFromGallery() {
        ImagePicker.with(this)
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent {
                isPictureAvailable = true
                startForProfileImageResult.launch(it)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val RESULT_ERROR = 101
    }
}