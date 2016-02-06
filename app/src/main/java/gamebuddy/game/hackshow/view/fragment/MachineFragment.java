package gamebuddy.game.hackshow.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.kennyc.view.MultiStateView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.core.provider.DataType;
import gamebuddy.game.hackshow.model.BlankBottom;
import gamebuddy.game.hackshow.model.CommandInfo;
import gamebuddy.game.hackshow.model.CommandInput;
import gamebuddy.game.hackshow.view.view.adapter.BlankBottomView;
import gamebuddy.game.hackshow.view.view.adapter.CommandInfoView;
import gamebuddy.game.hackshow.view.view.adapter.CommandInputView;
import io.nlopez.smartadapters.SmartAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import io.nlopez.smartadapters.utils.ViewEventListener;

import static com.kennyc.view.MultiStateView.VIEW_STATE_CONTENT;
import static com.kennyc.view.MultiStateView.VIEW_STATE_ERROR;
import static com.kennyc.view.MultiStateView.VIEW_STATE_LOADING;

/**
 * describe
 * created by tindle
 * created time 15/12/10 下午4:10
 */
public class MachineFragment extends BaseFragment implements ViewEventListener<Object> {

    RecyclerMultiAdapter adapter;

    @Bind(R.id.multiStateView)
    MultiStateView multiStateView;

    @Bind(R.id.refresh)
    MaterialRefreshLayout refreshView;

    @Bind(R.id.recycler_view)
    RecyclerView topicListView;

    Date maxRefreshedAt = null;

    private String objectId = "";
    List<Object> currentItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_normal_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        topicListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = SmartAdapter.empty()
                .map(CommandInfo.class, CommandInfoView.class)
                .map(CommandInput.class, CommandInputView.class)
                .map(BlankBottom.class, BlankBottomView.class)
                .listener(this)
                .into(topicListView);

        refreshView.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshView.finishRefresh();
//                getPresenter().refresh(MachineFragment.this, objectId);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
//                getPresenter().nextPage(MachineFragment.this, maxRefreshedAt, objectId);
            }
        });
        refreshView.setLoadMore(false);
        multiStateView.setViewState(VIEW_STATE_LOADING);

        lazyLoad();
    }

    protected void lazyLoad() {
//        getPresenter().refresh(MachineFragment.this, objectId);

        List<Object> items = new ArrayList<>();
        items.add(new CommandInfo());
        items.add(new CommandInput());
        items.add(new BlankBottom());
        adapter.setItems(items);
        topicListView.scrollToPosition(items.size()-1);
        multiStateView.setViewState(VIEW_STATE_CONTENT);
        refreshView.finishRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected String getTitle() {
        return "Video";
    }


    public void onNetworkError(Throwable throwable, int pageIndex) {
        if (pageIndex == DataType.DATA_FETCH_REFRESH) {
            multiStateView.setViewState(VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onViewEvent(int actionId, Object obj, int position, View view) {

    }

    public void retry() {
        multiStateView.setViewState(VIEW_STATE_LOADING);
//        getPresenter().refresh(MachineFragment.this, objectId);
    }


}
