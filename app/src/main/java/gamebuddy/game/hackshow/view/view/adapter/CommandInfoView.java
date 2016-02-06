package gamebuddy.game.hackshow.view.view.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import gamebuddy.game.hackshow.R;
import gamebuddy.game.hackshow.model.CommandInfo;
import io.nlopez.smartadapters.views.BindableLinearLayout;

/**
 * describe
 * created by tindle
 * created time 16/1/14 上午10:42
 */
public class CommandInfoView extends BindableLinearLayout<CommandInfo>{

    @Bind(R.id.tv_content)
    TextView tv_content;

    public CommandInfoView(Context context){
        super(context);
    }

    @Override
    public int getOrientation() {
        return LinearLayout.VERTICAL;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_command_info;
    }

    @Override
    public void bind(CommandInfo commandInfo) {

    }

}
