package client;

import interfaces.local.HistoryServiceLocal;
import interfaces.model.QueryHistory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class HistoryViewModel implements Serializable {

    @ManagedProperty(value = "#{userViewModel}")
    private UserViewModel userViewModel;

    @Inject
    private HistoryServiceLocal historyServiceLocal;

    private List<QueryHistory> historyList = new ArrayList<>();

    @PostConstruct
    public void init() {
        historyList = historyServiceLocal.findQueryHistory(userViewModel.getCurrentUser(), null, null);
    }

    public void clearHistory() {
        historyServiceLocal.clearHistory(userViewModel.getCurrentUser());
        historyList.clear();
    }

    public List<QueryHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<QueryHistory> historyList) {
        this.historyList = historyList;
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public void setUserViewModel(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
    }
}
