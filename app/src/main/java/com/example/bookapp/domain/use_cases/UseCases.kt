package com.example.bookapp.domain.use_cases

import com.example.bookapp.domain.use_cases.get_all_books.GetAllBooksUseCase
import com.example.bookapp.domain.use_cases.get_all_jetpacks.GetAllJetpacksUseCase
import com.example.bookapp.domain.use_cases.get_all_xml.GetAllXmlUseCase
import com.example.bookapp.domain.use_cases.get_selected_book.GetSelectedBookUseCase
import com.example.bookapp.domain.use_cases.get_selected_jetpack.GetSelectedJetpackUseCase
import com.example.bookapp.domain.use_cases.get_selected_xmls.GetSelectXmlUseCase
import com.example.bookapp.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.bookapp.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.bookapp.domain.use_cases.search_books.SearchBooksUseCase
import com.example.bookapp.domain.use_cases.search_jetpack.SearchJetpackUseCase
import com.example.bookapp.domain.use_cases.search_xml.SearchXmlUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllBooksUseCase: GetAllBooksUseCase,
    val searchBooksUseCase: SearchBooksUseCase,
    val getSelectedBookUseCase: GetSelectedBookUseCase,
    val getAllJetpacksUseCase: GetAllJetpacksUseCase,
    val searchJetpacksUseCase: SearchJetpackUseCase,
    val getSelectedJetpackUseCase: GetSelectedJetpackUseCase,
    val getAllXmlUseCase: GetAllXmlUseCase,
    val searchXmlUseCase: SearchXmlUseCase,
    val getSelectXmlUseCase: GetSelectXmlUseCase
)