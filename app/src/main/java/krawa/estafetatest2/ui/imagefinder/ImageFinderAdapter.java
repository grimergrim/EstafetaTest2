package krawa.estafetatest2.ui.imagefinder;

import krawa.estafetatest2.R;
import krawa.estafetatest2.model.Image;
import krawa.estafetatest2.ui.base.BaseEndlessListAdapter;
import krawa.estafetatest2.ui.base.BaseListFragmentPresenter;

public class ImageFinderAdapter extends BaseEndlessListAdapter<Image> {


    public ImageFinderAdapter(BaseListFragmentPresenter presenter) {
        super(presenter);
    }

    @Override
    protected int compare(Image item1, Image item2) {
        if(item1.equals(item2)) return 0;
        return -1;
    }

    @Override
    protected boolean areContentsTheSame(Image oldItem, Image newItem) {
        return false;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.image_finder_item;
    }
}
