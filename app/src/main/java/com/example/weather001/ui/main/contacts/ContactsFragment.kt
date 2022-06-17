package com.example.weather001.ui.main.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weather001.R
import com.example.weather001.databinding.FragmentContactsBinding


class ContactsFragment : Fragment() {
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContacts()
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.need_permissions_to_read_contacts),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission() {
        context?.let { notNullContext ->
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    notNullContext,
                    Manifest.permission.READ_CONTACTS
                ) -> {
                    getContacts()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun getContacts() {
        context?.let { nonNullContext ->
            // Отправляем запрос на получение контактов и получаем ответ в виде Cursor'а
            val cursorWithContacts: Cursor? = nonNullContext.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )

            cursorWithContacts?.let { cursor ->
                for (i in 0..cursor.count) {
                    // Переходим на позицию в Cursor'е
                    if (cursor.moveToPosition(i)) {
                        // Берём из Cursor'а столбец с именем
                        val columnIndex =
                            cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        if (columnIndex >= 0) {
                            val name = cursor.getString(columnIndex)
                            addView(name)
                        }
                    }
                }
            }
            cursorWithContacts?.close()
        }
    }

    private fun requestPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun addView(name: String) = with(binding) {
        containerForContacts.addView(
            TextView(requireContext()).apply {
                text = name
                textSize = resources.getDimension(R.dimen.main_container_text_size)
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = ContactsFragment()
    }
}
