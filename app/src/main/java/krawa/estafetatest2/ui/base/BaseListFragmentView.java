package krawa.estafetatest2.ui.base;

import java.util.List;

public interface BaseListFragmentView<T> extends BaseFragmentView {

    void updateList(List<T> items, boolean hasMore, boolean clear);
    void showListProgress(boolean show);
    void setEmptyText(String text);
    void removeAll();
    void removeItem(T item);
    void addItem(T item);
    void updateItem(T item);
    void setListPosition(int position);
    void setListTopPadding(int padding);

}
