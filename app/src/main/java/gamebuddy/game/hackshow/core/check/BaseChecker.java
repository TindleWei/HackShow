package gamebuddy.game.hackshow.core.check;

/**
 * describe
 * created by tindle
 * created time 16/2/28 上午10:45
 */
public class BaseChecker {

    public interface Callback {
        void onBackResult(String result);
    }

    Callback mCallback;

    public void putBack(String result){
        if(mCallback==null){
            return;
        } else {
            mCallback.onBackResult(result);
        }
    }

}
