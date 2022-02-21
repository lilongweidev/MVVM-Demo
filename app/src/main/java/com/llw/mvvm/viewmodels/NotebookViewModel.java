package com.llw.mvvm.viewmodels;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;

import com.llw.mvvm.db.bean.Notebook;
import com.llw.mvvm.repository.NotebookRepository;

import java.util.List;

/**
 * NotebookViewModel
 * @author llw
 * {@link com.llw.mvvm.ui.activity.NotebookActivity}
 */
public class NotebookViewModel extends BaseViewModel {

    private final NotebookRepository notebookRepository;

    public LiveData<List<Notebook>> notebooks;

    @ViewModelInject
    NotebookViewModel(NotebookRepository notebookRepository){
        this.notebookRepository = notebookRepository;
    }

    public void getNotebooks() {
        notebooks = notebookRepository.getNotebooks();
        failed = notebookRepository.failed;
    }

    /**
     * 删除笔记
     */
    public void deleteNotebook(Notebook... notebook) {
        notebookRepository.deleteNotebook(notebook);
        failed = notebookRepository.failed;
    }

    /**
     * 搜索笔记
     * @param input 输入内容
     */
    public void searchNotebook(String input) {
        notebooks = notebookRepository.searchNotebook(input);
        failed = notebookRepository.failed;
    }
}
